function checknull()
{
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
    /*if((document.frmGeneralLedgerSystem.cmbAccHeadCode.value=="") || (document.frmGeneralLedgerSystem.cmbAccHeadCode.value.length<=0) || (document.frmGeneralLedgerSystem.cmbAccHeadCode.value=="0"))
    {
        alert("Please Select Account Head Code");
        document.frmGeneralLedgerSystem.cmbAccHeadCode.focus();
        return false;
    }*/
 return true;
}
function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
   
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48||unicode>57 ) 
            return false 
    }
 }
 function ListAll()
{
      
        {
        var Acc_UnitCode=document.frmTrialBalance.cmbAcc_UnitCode.value;
        //var OffCode=document.frmTrialBalance.cmbOffice_code.value;
        //var FinanYr=document.frmTrialBalance.cmbFinancialYear.value;
        var CashbookYear=document.frmTrialBalance.txtCB_Year.value;
        var CashbookMonth=document.frmTrialBalance.txtCB_Month.value;
        //var cmbSL_Type=document.getElementById("cmbMas_SL_type").value;
       // var cmbMas_SL_Code=document.getElementById("cmbMas_SL_Code").value; 
        listPopupwindow= window.open("ViewFreeze_GL.jsp?cmbAcc_UnitCode="+Acc_UnitCode+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth,"mywindow1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
        listPopupwindow.moveTo(250,250); 
        document.frmTrialBalance.txtCB_Year.disabled=true;
        document.frmTrialBalance.txtCB_Month.disabled=true;
        }
}
