function checknull()
{
    if((document.frmA26Report.cmbAcc_UnitCode.value=="") || (document.frmA26Report.cmbAcc_UnitCode.value.length<=0) || (document.frmA26Report.cmbAcc_UnitCode.value=="0"))
    {
        alert("Please Select Accounting Unit");
        document.frmA26Report.cmbAcc_UnitCode.focus();
        return false;
    }
    if((document.frmA26Report.cmbOffice_code.value=="") || (document.frmA26Report.cmbOffice_code.value.length<=0) || (document.frmA26Report.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.frmA26Report.cmbOffice_code.focus();
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

function callSupp()
{
	if(document.frmA26Report.reporttype[0].checked==true)
		{
		document.getElementById("dispsupno1").style.display="none";
		document.getElementById("dispsupno2").style.display="none";
		}
	else if(document.frmA26Report.reporttype[1].checked==true)
		{
		document.getElementById("dispsupno1").style.display="none";
		document.getElementById("dispsupno2").style.display="none";
		}
	else if(document.frmA26Report.reporttype[2].checked==true)
	{
		document.getElementById("dispsupno1").style.display="block";
		document.getElementById("dispsupno2").style.display="block";
	}
}