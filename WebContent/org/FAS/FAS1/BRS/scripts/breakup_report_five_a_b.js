function AjaxFunction() {
    		var xmlrequest = false;
    		try {
    			xmlrequest = new ActiveXObject("Msxml2.XMLHTTP");
    		} catch (e1) {
    			try {
    				xmlrequest = new ActiveXObject("Microsoft.XMLHTTP");
    			} catch (e2) {
    				xmlrequest = false;
    			}
    		}
    		if (!xmlrequest && typeof XMLHttpRequest != 'undefined') {
    			xmlrequest = new XMLHttpRequest();
    		}
    		return xmlrequest;
    	}

function loadyear_month()
{
      var today= new Date(); 
      var day=today.getDate();
      var month=today.getMonth();
      month=month+1;
      var year=today.getYear();
      if(year < 1900) year += 1900;
                 
     document.formbrs_ob_report.txtCB_Year.value=year;

}
function exit()
{
   self.close();
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
            return false; 
    }
}
function AjaxFunction() {
		var xmlrequest = false;
		try {
			xmlrequest = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e1) {
			try {
				xmlrequest = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e2) {
				xmlrequest = false;
			}
		}
		if (!xmlrequest && typeof XMLHttpRequest != 'undefined') {
			xmlrequest = new XMLHttpRequest();
		}
		return xmlrequest;
	}
function LoadBankAccountNumber_opr()
{  	
	   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	 
	   var url="../../../../../BRSReport_Part2A?command=LoadBankAccountNumber&cmbAcc_UnitCode="+cmbAcc_UnitCode;
	   if(document.getElementById("txtOprCode"))
	   {
		 	var cashbook_yr=document.getElementById("txtCB_Year").value;	 
		 	var cashbook_mn=document.getElementById("txtCB_Month").value;	 
		 	url+="&option=nontwad";
	   }
	//   alert(url);
	   var req=getTransport();
	   req.open("GET",url,true); 
	   req.onreadystatechange=function()
	   {
	    	 LoadBankAccountNumberRes(req);
	   }   
       req.send(null);
	  
}


function LoadBankAccountNumberRes(req)
{
 
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             var tagcommand=baseResponse.getElementsByTagName("command")[0];
             var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="LoadBankAccountNumber")
            {
            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	 
            	 if(flag=="success")
            	    {
            	          
            	           
            	           /** Bank Account Number Object to find length */ 
            	           var acc_no=baseResponse.getElementsByTagName("acc_no");
            	           
            	           /** Get Combo box Object */
            	           var cmbBankAccNo = document.getElementById("cmbBankAccNo");
            	        //   alert(acc_no.length);
            	            for(var k=0;k<acc_no.length;k++)
            	            {
            	            	bank_ac_no[k]=baseResponse.getElementsByTagName("acc_no")[k].firstChild.nodeValue;
            	            	acc_desc[k]=baseResponse.getElementsByTagName("acc_desc")[k].firstChild.nodeValue;
            	            	bank_name[k]=baseResponse.getElementsByTagName("bank_name")[k].firstChild.nodeValue;
            	            	branch_name[k]=baseResponse.getElementsByTagName("branch_name")[k].firstChild.nodeValue;
            	            	bank_id[k] =baseResponse.getElementsByTagName("bank_id")[k].firstChild.nodeValue;
            	            	branch_id[k] =baseResponse.getElementsByTagName("branch_id")[k].firstChild.nodeValue;
            	            	opr_mode[k] =baseResponse.getElementsByTagName("opr_mode")[k].firstChild.nodeValue;            	            	
            	            }
            	         
            	            cmbBankAccNo.innerHTML="";
            	            var option=document.createElement("OPTION");
            	            option.text="--Select Bank Acc. Number--";
            	            option.value="";
            	            try
            	            {
            	            	cmbBankAccNo.add(option);
            	            }catch(errorObject)
            	            {
            	            	cmbBankAccNo.add(option,null);
            	            }
            	            
            	            for(var k=0;k<acc_no.length;k++)
            	            {   
            	                  var option=document.createElement("OPTION");
            	                  option.text=acc_desc[k];
            	                  option.value=bank_ac_no[k];
            	                  try
            	                  {
            	                	  cmbBankAccNo.add(option);
            	                  }
            	                  catch(errorObject)
            	                  {
            	                	  cmbBankAccNo.add(option,null);
            	                  }
            	            }
            	    }
              }
              else
              {
            	    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;             	 
             	    if(flag=="success")
             	    {
             	    		var acc_head_code=baseResponse.getElementsByTagName("acc_head_code")[0].firstChild.nodeValue;
             	    		document.getElementById("txtOprCode").value=acc_head_code;
             	    }
              }
        
        }
    }
}

function checkNull()
{
	if(document.getElementById("txtCB_Year").value=="")
	{
		alert("Enter Year");
		return false;
	}
	if(document.getElementById("txtCB_Month").value=="s")
	{
		alert("Select Month");
		return false;
	}
	if(document.getElementById("cmbBankAccNo").value=="")
	{
		alert("Select A/c No");
		return false;
	}
}