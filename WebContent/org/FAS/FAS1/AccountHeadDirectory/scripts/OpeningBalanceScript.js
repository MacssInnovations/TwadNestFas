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

////////////////

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
   fetchingValues();
   return true;
}

////////////////////////////

function Exit()
 {
    window.open('','_parent','');
    window.close();
 }
var listPopupwindow;
function ListAll()
{
        if(document.OpeningBalForm.txtFinanYr.value.length!=9)
        {
            alert("Enter the financial year correctly");
            return false;
        }
        //alert(" the details of Selected Financial year");
        if(confirm("Do you want to view the Selected Financial year and Cashbook Month details"))
        {
        var Acc_UnitCode=document.OpeningBalForm.cmbAcc_UnitCode.value;
        var OffCode=document.OpeningBalForm.comOffCode.value;
        var FinanYr=document.OpeningBalForm.txtFinanYr.value;
        var CashbookYear=document.OpeningBalForm.txtCB_Year.value;
        var CashbookMonth=document.OpeningBalForm.txtCB_Month.value;
        listPopupwindow= window.open("OpeningBalanceListJSP.jsp?cmbAcc_UnitCode="+Acc_UnitCode+"&cmbOffice_code="+OffCode+"&txtFinanYr="+FinanYr+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth,"mywindow1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
        listPopupwindow.moveTo(250,250);    
        }
       // alert("disable");
      document.OpeningBalForm.txtCB_Year.disabled=true;
     document.OpeningBalForm.txtCB_Month.disabled=true;
}

window.onunload=function()
{
if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
if (listPopupwindow && listPopupwindow.open && !listPopupwindow.closed) listPopupwindow.close();
}

function clearall()
{
        //document.OpeningBalForm.txtFinanYr.value="";
       // document.OpeningBalForm.txtCB_Year.value="";
        //document.OpeningBalForm.txtCB_Month.value="";
        document.OpeningBalForm.cmbAcHeadCode.value="";
        document.OpeningBalForm.cmbMajorGroup.value="";
        document.OpeningBalForm.cmbMinorGroup.value="";
        document.OpeningBalForm.cmbSubGroup1.value="";
        document.OpeningBalForm.cmbSubGroup2.value="";
        document.OpeningBalForm.txtDebit.value="";
        document.OpeningBalForm.txtCredit.value="";
        document.OpeningBalForm.txtYrCredit.value="";
        document.OpeningBalForm.txtYrDebit.value="";
        //document.OpeningBalForm.txtFinanYr.readOnly=false;
        document.OpeningBalForm.cmbAcHeadCode.readOnly=false;
        
        document.OpeningBalForm.txtOpenBal.value="";
       document.OpeningBalForm.txtCurrMonDebit.value="";
       document.OpeningBalForm.txtCurrMonCredit.value="";
       document.OpeningBalForm.txtCloseBal.value="";
       document.OpeningBalForm.txtDrLUpdate.value="";
       document.OpeningBalForm.txtCrLUpdate.value="";
       document.OpeningBalForm.radOpenBalCrDrInd[0].checked=true;
       document.OpeningBalForm.radCloseBalCrDrInd[0].checked=true;
     var d=document.getElementById("cmdAdd");
     d.style.display="block";
    
     var d1=document.getElementById("cmdUpdate");
     d1.style.display="none";
    
    var d2=document.getElementById("cmdDelete");
    d2.style.display="none";
    
   /* document.OpeningBalForm.txtDebit.disabled=false;
    document.OpeningBalForm.txtYrDebit.disabled=false;   
    document.OpeningBalForm.txtCredit.disabled=false;
    document.OpeningBalForm.txtYrCredit.disabled=false;   
    */
}

function clearAccountHead()
{
    document.OpeningBalForm.cmbAcHeadCode.value="";
}


function fetchingValues()
{
             var AcHeadCode=document.OpeningBalForm.cmbAcHeadCode.value;
             var Acc_UnitCode=document.OpeningBalForm.cmbAcc_UnitCode.value;
             var OffCode=document.OpeningBalForm.comOffCode.value;
             var FinanYr=document.OpeningBalForm.txtFinanYr.value;
             var CashbookYear=document.OpeningBalForm.txtCB_Year.value;
             var CashbookMonth=document.OpeningBalForm.txtCB_Month.value;
            if(document.OpeningBalForm.txtFinanYr.value.length!=9)
            {
                alert("Select the Finance year first");
                document.OpeningBalForm.cmbAcHeadCode.value="";
                document.OpeningBalForm.txtFinanYr.focus();
                return false;
            } 
            if(AcHeadCode.length>=6 && FinanYr!="")
            {
                var url="../../../../../OpeningBalServ.view?Command=fetchingValues&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&txtFinanYr="+FinanYr+"&cmbAcHeadCode="+AcHeadCode+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }
}



function doFunction(command,param)
{
    var Acc_UnitCode=document.OpeningBalForm.cmbAcc_UnitCode.value;
    var OffCode=document.OpeningBalForm.comOffCode.value;
    var FinanYr=document.OpeningBalForm.txtFinanYr.value;
    var AcHeadCode=document.OpeningBalForm.cmbAcHeadCode.value;
    var MajorGroup=document.OpeningBalForm.cmbMajorGroup.value;
    var MinorGroup=document.OpeningBalForm.cmbMinorGroup.value;
    var SubGroup1=document.OpeningBalForm.cmbSubGroup1.value;
    var SubGroup2=document.OpeningBalForm.cmbSubGroup2.value;
    var Credit=document.OpeningBalForm.txtCredit.value;
    var Debit=document.OpeningBalForm.txtDebit.value;
    var YrCredit=document.OpeningBalForm.txtYrCredit.value;
    var YrDebit=document.OpeningBalForm.txtYrDebit.value;
    var OpenBal=document.OpeningBalForm.txtOpenBal.value;
    var OpenBalInd;
    if(document.OpeningBalForm.radOpenBalCrDrInd[0].checked==true)
    {
        OpenBalInd=document.OpeningBalForm.radOpenBalCrDrInd[0].value;
    }
    else
    {
        OpenBalInd=document.OpeningBalForm.radOpenBalCrDrInd[1].value;
    }
    
    var CurMonDebit=document.OpeningBalForm.txtCurrMonDebit.value;
    var CurMonCredit=document.OpeningBalForm.txtCurrMonCredit.value;
    var CloseBal=document.OpeningBalForm.txtCloseBal.value;
    var CloseBalInd;
    if(document.OpeningBalForm.radCloseBalCrDrInd[0].checked==true)
    {
        CloseBalInd=document.OpeningBalForm.radCloseBalCrDrInd[0].value;
    }
    else
    {
        CloseBalInd=document.OpeningBalForm.radCloseBalCrDrInd[1].value;
    }
    
    var DrLastUpdateDate=document.OpeningBalForm.txtDrLUpdate.value;
    var CrLastUpdateDate=document.OpeningBalForm.txtCrLUpdate.value;
    var CashbookYear=document.OpeningBalForm.txtCB_Year.value;
    var CashbookMonth=document.OpeningBalForm.txtCB_Month.value;
    if(command=="Add")
    {
            if(nullcheck())
            {
                var url="../../../../../OpeningBalServ.view?Command=Add&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&txtFinanYr="+FinanYr+"&cmbAcHeadCode="+AcHeadCode+"&cmbMajorGroup="+MajorGroup+"&cmbMinorGroup="+MinorGroup+"&cmbSubGroup1="+SubGroup1+"&cmbSubGroup2="+SubGroup2+"&txtCredit="+Credit+"&txtDebit="+Debit+"&txtYrCredit="+YrCredit+"&txtYrDebit="+YrDebit+"&OpenBal="+OpenBal+"&OpenBalInd="+OpenBalInd+"&CurMonDebit="+CurMonDebit+"&CurMonCredit="+CurMonCredit+"&CloseBal="+CloseBal+"&CloseBalInd="+CloseBalInd+"&DrLastUpdateDate="+DrLastUpdateDate+"&CrLastUpdateDate="+CrLastUpdateDate+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
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
                if(nullcheck())
                {
                    var url="../../../../../OpeningBalServ.view?Command=Update&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&txtFinanYr="+FinanYr+"&cmbAcHeadCode="+AcHeadCode+"&cmbMajorGroup="+MajorGroup+"&cmbMinorGroup="+MinorGroup+"&cmbSubGroup1="+SubGroup1+"&cmbSubGroup2="+SubGroup2+"&txtCredit="+Credit+"&txtDebit="+Debit+"&txtYrCredit="+YrCredit+"&txtYrDebit="+YrDebit+"&OpenBal="+OpenBal+"&OpenBalInd="+OpenBalInd+"&CurMonDebit="+CurMonDebit+"&CurMonCredit="+CurMonCredit+"&CloseBal="+CloseBal+"&CloseBalInd="+CloseBalInd+"&DrLastUpdateDate="+DrLastUpdateDate+"&CrLastUpdateDate="+CrLastUpdateDate+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
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
        if(confirm("Do u really want to delete the record"))
        {
                if(nullcheck())
                {
                    var url="../../../../../OpeningBalServ.view?Command=Delete&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&txtFinanYr="+FinanYr+"&cmbAcHeadCode="+AcHeadCode+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
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
            alert("Records are not Deleted");
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
              
            if(Command=="fetchingValues")
            {
                fetchingValuesRow(baseResponse);
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
        }
    }
}


function fetchingValuesRow(baseResponse)
   {
       var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {
       var majorDesc=baseResponse.getElementsByTagName("majorDesc")[0].firstChild.nodeValue;
       var minordesc=baseResponse.getElementsByTagName("minordesc")[0].firstChild.nodeValue;
       var subdesc1=baseResponse.getElementsByTagName("subdesc1")[0].firstChild.nodeValue;
       var subdesc2=baseResponse.getElementsByTagName("subdesc2")[0].firstChild.nodeValue;
    
       document.OpeningBalForm.cmbMajorGroup.value=majorDesc;
       document.OpeningBalForm.cmbMinorGroup.value=minordesc;
       if(subdesc1=="null")
       subdesc1="";
       if(subdesc2=="null")
       subdesc2="";
       
       document.OpeningBalForm.cmbSubGroup1.value=subdesc1;
       document.OpeningBalForm.cmbSubGroup2.value=subdesc2;
       
       }
       else if(flag=="exist")
       {
            alert("Account Head Code already exist ");
            document.OpeningBalForm.cmbAcHeadCode.value="";
             document.OpeningBalForm.cmbMajorGroup.value="";
            document.OpeningBalForm.cmbMinorGroup.value="";
            document.OpeningBalForm.cmbSubGroup1.value="";
            document.OpeningBalForm.cmbSubGroup2.value="";
       }
       else
       {
         alert("Account Head code doesn't exist");
         document.OpeningBalForm.cmbAcHeadCode.value="";
         document.OpeningBalForm.cmbMajorGroup.value="";
         document.OpeningBalForm.cmbMinorGroup.value="";
         document.OpeningBalForm.cmbSubGroup1.value="";
         document.OpeningBalForm.cmbSubGroup2.value="";
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
    else
    {
        alert("Records r not inserted");
    }
}


function updateRow(baseResponse)
{
 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {   
         alert("Record updated successfully");
         document.OpeningBalForm.txtCB_Year.disabled=false;
     document.OpeningBalForm.txtCB_Month.disabled=false;
         clearall();
    }
    else
    {
        alert("Record r not updated");
    }
}

function deleteRow(baseResponse)
{
 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {   
         alert("Records deleted successfully");
         clearall();
    }
    else
    {
        alert("Record r not deleted");
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
function loadDate()
{
    var ddate=new Date();
    var mon= ddate.getMonth();
    var yr1 = ddate.getYear();
    var yr2= ddate.getYear();
      if(yr1 < 1900) yr1 += 1900;
       if(yr2 < 1900) yr2 += 1900;
  
    mon=parseInt(mon)+1;
    if(mon<=3 && mon>=1)
        yr1=parseInt(yr1)-1;
    else if(mon>=4 && mon<=12)
        yr2=parseInt(yr2)+1;
          
    document.OpeningBalForm.txtFinanYr.value=yr1+"-"+yr2;
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
///////////////////////////////////////////  valid amount or not
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

function nullcheck()
{

    if(document.OpeningBalForm.txtFinanYr.value.length!=9)
    {
        alert("Select the Finance year ");
        document.OpeningBalForm.txtFinanYr.focus();
        return false;
    }
    if(document.OpeningBalForm.cmbAcHeadCode.value.length==0)
    {
        alert("Enter the Account Head Code");
        document.OpeningBalForm.cmbAcHeadCode.focus();
        return false;
    }
                /*
                if(document.OpeningBalForm.txtDebit.value.length==0)
                {
                    alert("Enter the Upto Date Debit Balance ");
                    document.OpeningBalForm.txtDebit.focus();
                }
                if(document.OpeningBalForm.txtCredit.value.length==0)
                {
                    alert("Enter the Upto Date Credit Balance ");
                    document.OpeningBalForm.txtCredit.focus();
                }
                if(document.OpeningBalForm.txtYrCredit.value.length==0)
                {
                    alert("Enter the Current Year Credit Balance ");
                    document.OpeningBalForm.txtYrCredit.focus();
                }
                if(document.OpeningBalForm.txtYrDebit.value.length==0)
                {
                    alert("Enter the Current Year Debit Balance ");
                    document.OpeningBalForm.txtYrDebit.focus();
                }*/
                
    return true;
}


//Change Date 30-Nov-2006
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

function getCurrentYear() {
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
  }

  function getCurrentMonth() {
    return new Date().getMonth() + 1;
  } 

  function getCurrentDay() {
    return new Date().getDate();
  }

function checkdt1(t)
{
 
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){
        //exception  start
      
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to '+ _Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
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
         
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
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
            //t.focus();
            return false
    }
    
} 

//End Change Date 30-Nov-2006

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
			var selectdiv=document.getElementById('comOffCode');
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
			document.getElementById('comOffCode').selectedIndex=1;
		}else{
			alert("There is no accounting for office code for this accounting unit code");
		}
	}else{
		alert("Process Failure");
	}
	
}