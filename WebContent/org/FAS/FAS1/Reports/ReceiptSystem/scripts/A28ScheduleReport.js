function checknull()
{

    if((document.frmA28ScheduleReport.cmbOffice_code.value=="") || (document.frmA28ScheduleReport.cmbOffice_code.value.length<=0) || (document.frmA28ScheduleReport.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.frmA28ScheduleReport.cmbOffice_code.focus();
        return false;
    
    }
    if((document.getElementById("txtCB_Year").value.length!=4)|| (document.getElementById("txtCB_Year").value.length<=0) ||(document.getElementById("txtCB_Year").value==""))
    {
        alert("Enter the correct year");
        document.getElementById("txtCB_Year").focus();
        return false;
    }
     if((document.getElementById("txtCB_Month").value=="")||(document.frmA28ScheduleReport.txtCB_Month.value==0))
    {
        alert("Select a Month");	
        return false;
    }
    
   
 return true;
}

function numbersonly(e)
{   
        var unicode=e.charCode? e.charCode : e.keyCode;
         if(unicode==13)
        {
          
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false;
        }
}