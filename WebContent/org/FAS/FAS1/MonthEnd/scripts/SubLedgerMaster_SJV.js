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
   /* if((document.frmGeneralLedgerSystem.cmbMas_SL_type.value=="") || (document.frmGeneralLedgerSystem.cmbMas_SL_type.value.length<=0) || (document.frmGeneralLedgerSystem.cmbMas_SL_type.value=="0"))
    {
        alert("Please Select Sub-Ledger Type");
        document.frmGeneralLedgerSystem.cmbMas_SL_type.focus();
        return false;
    }*/
    
    if(document.frmGeneralLedgerSystem.txtsupplement_no=="")
    	{
    	alert("Please Select the Supplement No");
    	return false;
    	}
 return true;
}

function numbersonly(e)
{   
        var unicode=e.charCode? e.charCode : e.keyCode
         if(unicode==13)
        {
          //try{t.blur();}catch(e){}
          //return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
}