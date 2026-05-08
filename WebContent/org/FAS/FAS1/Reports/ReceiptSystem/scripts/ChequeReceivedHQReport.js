
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
            return false; 
    }
 }
 
 
function btncancel()
{

 self.close();
}

function checknull()
{

    if((document.getElementById("txtCB_YearFrom").value.length!=4) || (document.getElementById("txtCB_YearFrom").value.length<=0) ||(document.getElementById("txtCB_YearFrom").value==""))
    {
        alert("Enter the correct CB From year");
        document.getElementById("txtCB_YearFrom").focus();
        return false;
    }
     if((document.getElementById("txtCB_MonthFrom").value==""))
    {
        alert("Select CB From month");
        return false;
    }
     if((document.getElementById("txtCB_YearTo").value.length!=4) || (document.getElementById("txtCB_YearTo").value.length<=0) ||(document.getElementById("txtCB_YearTo").value==""))
     {
         alert("Enter the correct CB To year ");
         document.getElementById("txtCB_YearTo").focus();
         return false;
     }
      if(document.getElementById("txtCB_MonthTo").value=="")
     {
         alert("Select CB To month");
         return false;
     }
 return true;
}

function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmReceivedRegisterHQ.txtCB_YearFrom.value=year;
        document.frmReceivedRegisterHQ.txtCB_MonthFrom.value=month;
        document.frmReceivedRegisterHQ.txtCB_YearTo.value=year;
        document.frmReceivedRegisterHQ.txtCB_MonthTo.value=month;
        
         }