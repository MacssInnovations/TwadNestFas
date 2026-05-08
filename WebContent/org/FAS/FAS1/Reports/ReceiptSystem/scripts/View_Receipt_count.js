
//Null check Validation
function nullcheck()
{
    if((document.frmReport.cmbAcc_UnitCode.value=="") || (document.frmReport.cmbAcc_UnitCode.value.length<=0) || (document.frmReport.cmbAcc_UnitCode.value=="0"))
    {
        alert("Please Select Accounting Unit");
        document.frmReport.cmbAcc_UnitCode.focus();
        return false;
    }
    if((document.frmReport.cmbOffice_code.value=="") || (document.frmReport.cmbOffice_code.value.length<=0) || (document.frmReport.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.frmReport.cmbOffice_code.focus();
        return false;
    
    }
   
    
   /* if((document.frmReport.txtfromdate.value=="") || (document.frmReport.txtfromdate.value.length<=0))
    {
        alert("Please Enter From Date");
        document.frmReport.txtfromdate.focus();
        return false;
    }
    if((document.frmReport.txttodate.value=="") || (document.frmReport.txttodate.value.length<=0))
    {
        alert("Please Enter To Date");
        document.frmReport.txttodate.focus();
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