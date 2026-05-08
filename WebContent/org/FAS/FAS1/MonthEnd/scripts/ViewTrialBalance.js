function checknull()
{
 
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
                return false 
        }
     }
     
     
function checkyear()

 {   
         
         var strYear=document.frmTrialBalance.txtCB_Year.value;
         var  year=document.frmTrialBalance.txtCB_Year.value;
      
                strYr=strYear
                var minYear=1900;
                var maxYear=2100;
                var today= new Date();
                var current_year=today.getFullYear();
               
                if (strYear.length != 4 || year==0 || year<minYear || year>maxYear)
                {
                        alert("Please enter a valid 4 digit year between 1900 and 2100 ");
                        document.frmTrialBalance.txtCB_Year.value="";
                        return false
                }
                else if(current_year<year)
                {
                        alert("Year should not exceed current year");
                        document.frmTrialBalance.txtCB_Year.value="";
                        return false;
                }
 }