
//Null check Validation
function nullcheck()
{
    if((document.LJV_Pending_Payment_Report.cmbAcc_UnitCode.value=="") || (document.LJV_Pending_Payment_Report.cmbAcc_UnitCode.value.length<=0) || (document.LJV_Pending_Payment_Report.cmbAcc_UnitCode.value=="0"))
    {
        alert("Please Select Accounting Unit");
        document.LJV_Pending_Payment_Report.cmbAcc_UnitCode.focus();
        return false;
    }
    if((document.LJV_Pending_Payment_Report.cmbOffice_code.value=="") || (document.LJV_Pending_Payment_Report.cmbOffice_code.value.length<=0) || (document.LJV_Pending_Payment_Report.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.LJV_Pending_Payment_Report.cmbOffice_code.focus();
        return false;
    
    }
   
    
   /* if((document.LJV_Pending_Payment_Report.txtfromdate.value=="") || (document.LJV_Pending_Payment_Report.txtfromdate.value.length<=0))
    {
        alert("Please Enter From Date");
        document.LJV_Pending_Payment_Report.txtfromdate.focus();
        return false;
    }
    if((document.LJV_Pending_Payment_Report.txttodate.value=="") || (document.LJV_Pending_Payment_Report.txttodate.value.length<=0))
    {
        alert("Please Enter To Date");
        document.LJV_Pending_Payment_Report.txttodate.focus();
        return false;
    }*/
   
    var txtCB_Year=document.getElementById("txtCB_Year").value;
    var txtCB_Month=document.getElementById("txtCB_Month").value;
    
    if(txtCB_Year.length!=4 || txtCB_Month.length==0)
    {
        alert("Specify the year(4 digit) and month");
        return false;
    }
return true;
}



///////////////////////////////////////  Numbers only fields
function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
   if(unicode==13)
    {
      //t.blur();
      //return true;-------------------- for taking action when press ENTER
    
    }
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48 || unicode>57 ) 
            return false 
    }
 }