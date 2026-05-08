function checknull()
{

    if((document.frmWorkExpenditure.cmbOffice_code.value=="") || (document.frmWorkExpenditure.cmbOffice_code.value.length<=0) || (document.frmWorkExpenditure.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.frmWorkExpenditure.cmbOffice_code.focus();
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
    /*if((document.frmWorkExpenditure.cmbAccHeadCode.value=="") || (document.frmWorkExpenditure.cmbAccHeadCode.value.length<=0) || (document.frmWorkExpenditure.cmbAccHeadCode.value=="0"))
    {
        alert("Please Select Account Head Code");
        document.frmWorkExpenditure.cmbAccHeadCode.focus();
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