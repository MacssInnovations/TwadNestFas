function checknull()
{
    if((document.frmA47Report.cmbAcc_UnitCode.value=="") || (document.frmA47Report.cmbAcc_UnitCode.value.length<=0) || (document.frmA47Report.cmbAcc_UnitCode.value=="0"))
    {
        alert("Please Select Accounting Unit");
        document.frmA47Report.cmbAcc_UnitCode.focus();
        return false;
    }
    if((document.frmA47Report.cmbOffice_code.value=="") || (document.frmA47Report.cmbOffice_code.value.length<=0) || (document.frmA47Report.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.frmA47Report.cmbOffice_code.focus();
        return false;
    
    }
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
    /*if((document.frmA47Report.cmbScheduleid.value=="") || (document.frmA47Report.cmbScheduleid.value.length<=0) || (document.frmA47Report.cmbScheduleid.value=="0"))
    {
        alert("Please Select Schedule id");
        document.frmA47Report.cmbScheduleid.focus();
        return false;
    
    }*/
    return true;
}


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