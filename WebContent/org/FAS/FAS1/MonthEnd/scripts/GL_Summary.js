function checknull()
{
    
    /** Accounting Unit ID */ 
    if((document.frmGLSummary.cmbAcc_UnitCode.value=="") || (document.frmGLSummary.cmbAcc_UnitCode.value.length<=0) || (document.frmGLSummary.cmbAcc_UnitCode.value=="0"))
    {
        alert("Please Select Accounting Unit ID");
        document.frmGLSummary.cmbAcc_UnitCode.focus();
        return false;    
    }
    
    
    /** Accounting Office Code */ 
    if((document.frmGLSummary.cmbOffice_code.value=="") || (document.frmGLSummary.cmbOffice_code.value.length<=0) || (document.frmGLSummary.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.frmGLSummary.cmbOffice_code.focus();
        return false;    
    }
    
    /** From CB Year */
    if((document.getElementById("txtCB_Year_From").value.length!=4)|| (document.getElementById("txtCB_Year_From").value.length<=0) ||(document.getElementById("txtCB_Year_From").value==""))
    {
        alert("Enter year");
        document.getElementById("txtCB_Year_From").focus();
        return false;
    }    
    
    /** From CB Month */
    if(document.getElementById("txtCB_Month_From").value=="")
    {
        alert("Select month");
        return false;
    }
    
    
    /** To CB Year */
    if((document.getElementById("txtCB_Year_To").value.length!=4)|| (document.getElementById("txtCB_Year_To").value.length<=0) ||(document.getElementById("txtCB_Year_To").value==""))
    {
        alert("Enter year");
        document.getElementById("txtCB_Year_To").focus();
        return false;
    }    
    
    /** To CB Month */
    if(document.getElementById("txtCB_Month_To").value=="")
    {
        alert("Select month");
        return false;
    }
    
    
    
     /** Account Head Code Type */
     if ( document.frmGLSummary.Acc_Head_Type[1].checked == true ) 
     {  
        if((document.getElementById("txtAcc_HeadCode").value.length!=6)|| (document.getElementById("txtAcc_HeadCode").value.length<=0) ||(document.getElementById("txtAcc_HeadCode").value==""))
        {
            alert("Please Enter Account Head Code");
            document.getElementById("txtAcc_HeadCode").focus();
            return false;
        } 
        if(document.getElementById("txtAcc_HeadDesc").value=="")
        {
            alert("Account Head Code Description not yet loaded");
            return false;
        }
     }  
    
    return true;
    
    
}


function numbersonly(e)
{   
        var unicode=e.charCode? e.charCode : e.keyCode
        if(unicode==13)
        {        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
}