function checknull()
{
    /*
    if((document.miscRep.cmbAcc_UnitCode.value=="") || (document.miscRep.cmbAcc_UnitCode.value.length<=0) || (document.miscRep.cmbAcc_UnitCode.value=="0"))
    {
        alert("Please Select Accounting Unit");
        document.miscRep.cmbAcc_UnitCode.focus();
        return false;
    }
    if((document.miscRep.cmbOffice_code.value=="") || (document.miscRep.cmbOffice_code.value.length<=0) || (document.miscRep.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.miscRep.cmbOffice_code.focus();
        return false;
    
    }*/
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