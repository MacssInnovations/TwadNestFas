function checknull()
{

    if((document.frmGeneralLedgerSystem.cmbOffice_code.value=="") || (document.frmGeneralLedgerSystem.cmbOffice_code.value.length<=0) || (document.frmGeneralLedgerSystem.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.frmGeneralLedgerSystem.cmbOffice_code.focus();
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
    if(document.getElementById("txtCB_Month").value<4 && document.getElementById("txtCB_Year").value<=2007)
    {
        alert("Operation not allowed for this year and month");
        return false;
    }
    if(document.getElementById("txtCB_Year").value<2007)
    {
        alert("Operation not allowed for this year and month");
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
                return false; 
        }
     }
 
     
function CheckYear()
{
//alert("CheckYear");
 var txtCB_Year=document.getElementById("txtCB_Year").value;
 var txtCB_Month=document.getElementById("txtCB_Month").value;
 var year = new Date().getYear();
 if(year < 1900) 
 year += 1900;
// if(txtCB_Year<year)
// {
// alert("Month and Year should be Greater than March "+year);
// document.getElementById("txtCB_Year").value="";
// }

}