function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}


function numbersonly1(e,t)
{
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
}


function check()
{

    var m=document.frmValidationSummaryRep.cmbmon.value;
    //alert('m...'+m);
    
    if(m==00)
    {
      alert('Please select the Month');
      document.frmValidationSummaryRep.cmbmon.focus();
      return false;
    }
  
  
   if(document.frmValidationSummaryRep.tyear.value=="" && document.frmValidationSummaryRep.tyear.value.length==0)
   {
     alert('Please enter the Year');
     document.frmValidationSummaryRep.tyear.focus();
     return false;
   }  
   
   return true;
}
    


