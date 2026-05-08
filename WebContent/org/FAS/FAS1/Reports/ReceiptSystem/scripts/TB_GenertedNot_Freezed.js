
//Null check Validation
function nullcheck()
{
    var txtCB_Year=document.getElementById("txtCB_Year").value;
    var txtCB_Month=document.getElementById("txtCB_Month").value;
    
    if(txtCB_Year.length!=4 || txtCB_Month.length==0)
    {
        alert("Specify the year(4 digit) and month");
        return false;
    }
return true;
}


///////////////////////////////////////  Numbers only fields
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