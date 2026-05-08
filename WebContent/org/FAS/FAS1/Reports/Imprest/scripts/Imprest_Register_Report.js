function checknull()
{

//    if((document.frmSubLedgerReport.cmbOffice_code.value=="") || (document.frmSubLedgerReport.cmbOffice_code.value.length<=0) || (document.frmSubLedgerReport.cmbOffice_code.value=="0"))
//    {
//        alert("Please Select Accounting Office Code");
//        document.frmSubLedgerReport.cmbOffice_code.focus();
//        return false;
//    
//    }
	if(document.frmSubLedgerReport.viewReport[0].checked==true){
	
		if(document.frmSubLedgerReport.txtRegionId.value=="0")
		{
			alert("Select Region");
			 return false;
		}
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

function hideOthers()
{ 
	if(document.frmSubLedgerReport.viewReport[0].checked==true){
		var t1=document.getElementById("unitdiv");
		t1.style.display="none";
		var d1=document.getElementById("unitdiv1");
		d1.style.display="none";
		var t2=document.getElementById("officediv");
		t2.style.display="none";
		var d2=document.getElementById("officediv1");
		d2.style.display="none";
		var t3=document.getElementById("regiondiv");
		t3.style.display="block";
		var d3=document.getElementById("regiondiv1");
		d3.style.display="block";
	}
	else if(document.frmSubLedgerReport.viewReport[1].checked==true){
		var t1=document.getElementById("unitdiv");
		t1.style.display="block";
		var d1=document.getElementById("unitdiv1");
		d1.style.display="block";
		var t2=document.getElementById("officediv");
		t2.style.display="block";
		var d2=document.getElementById("officediv1");
		d2.style.display="block";
		var t3=document.getElementById("regiondiv");
		t3.style.display="none";
		var d3=document.getElementById("regiondiv1");
		d3.style.display="none";
		LoadAccountingUnitID('FOR_LIST_1');
	}
	else if(document.frmSubLedgerReport.viewReport[2].checked==true){
		var t1=document.getElementById("unitdiv");
		t1.style.display="none";
		var d1=document.getElementById("unitdiv1");
		d1.style.display="none";
		var t2=document.getElementById("officediv");
		t2.style.display="none";
		var d2=document.getElementById("officediv1");
		d2.style.display="none";
		var t3=document.getElementById("regiondiv");
		t3.style.display="none";
		var d3=document.getElementById("regiondiv1");
		d3.style.display="none";
	}
	
}