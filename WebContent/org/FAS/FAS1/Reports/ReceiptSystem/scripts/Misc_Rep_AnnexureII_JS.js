function checknull()
{
     if((document.getElementById("txtfrom_CB_Year").value.length!=4)|| (document.getElementById("txtfrom_CB_Year").value.length<=0) ||(document.getElementById("txtfrom_CB_Year").value==""))
    {
        alert("Enter the correct from year");
        document.getElementById("txtfrom_CB_Year").focus();
        return false;
    }
     if(document.getElementById("txtfrom_CB_Month").value=="")
    {
        alert("Select a from month");
        return false;
    }
    
    if((document.getElementById("txtto_CB_Year").value.length!=4)|| (document.getElementById("txtto_CB_Year").value.length<=0) ||(document.getElementById("txtto_CB_Year").value==""))
    {
        alert("Enter the correct to year");
        document.getElementById("txtto_CB_Year").focus();
        return false;
    }
     if(document.getElementById("txtto_CB_Month").value=="")
    {
        alert("Select a to month");
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