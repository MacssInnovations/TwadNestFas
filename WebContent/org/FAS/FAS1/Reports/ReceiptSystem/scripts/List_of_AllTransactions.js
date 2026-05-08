function checknull()
{
 
    if((document.getElementById("from_txtCB_Year").value.length!=4)|| (document.getElementById("from_txtCB_Year").value.length<=0) ||(document.getElementById("from_txtCB_Year").value==""))
    {
        alert("Enter the correct year");
        document.getElementById("from_txtCB_Year").focus();
        return false;
    }
    
    if((document.getElementById("to_txtCB_Year").value.length!=4)|| (document.getElementById("to_txtCB_Year").value.length<=0) ||(document.getElementById("to_txtCB_Year").value==""))
    {
        alert("Enter the correct year");
        document.getElementById("to_txtCB_Year").focus();
        return false;
    }
    
    
    if(document.getElementById("from_txtCB_Month").value=="")
    {
        alert("Select From Cashbook Month");
        return false;
    }   
    
    if(document.getElementById("to_txtCB_Month").value=="")
    {
        alert("Select To Cashbook Month");
        return false;
    }   
    
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