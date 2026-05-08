function checknull()
{

    if((document.imprest_officeWise.cmbOffice_code.value=="") || (document.imprest_officeWise.cmbOffice_code.value.length<=0) || (document.imprest_officeWise.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.imprest_officeWise.cmbOffice_code.focus();
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
          //try{t.blur();}catch(e){}
          //return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false;
        }
}