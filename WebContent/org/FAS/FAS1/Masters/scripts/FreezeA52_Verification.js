/*
 *    Null Values Checking 
 */


function checknull()
{
    /*if((document.getElementById("txtCB_Year").value.length!=4)|| (document.getElementById("txtCB_Year").value.length<=0) ||(document.getElementById("txtCB_Year").value==""))
    {
        alert("Enter the correct year");
        document.getElementById("txtCB_Year").focus();
        return false;
    }
    if(document.getElementById("txtCB_Month").value=="")
    {
        alert("Select a month");
        return false;
    }*/
	
	 if(document.getElementById("fin_year").value=="")
	    {
	        alert("Select a financial year ");
	        return false;
	    }
    /*if((document.frmGeneralLedgerSystem.cmbAccHeadCode.value=="") || (document.frmGeneralLedgerSystem.cmbAccHeadCode.value.length<=0) || (document.frmGeneralLedgerSystem.cmbAccHeadCode.value=="0"))
    {
        alert("Please Select Account Head Code");
        document.frmGeneralLedgerSystem.cmbAccHeadCode.focus();
        return false;
    }*/
    
 
}

function getTransport()
{
         var req = false;
         try 
         {
                req= new ActiveXObject("Msxml2.XMLHTTP");
         }
         catch (e) 
         {
                try 
                {
                        req = new ActiveXObject("Microsoft.XMLHTTP");
                }
                catch (e2) 
                {
                        req = false;
                }
         }
         if (!req && typeof XMLHttpRequest != 'undefined') 
         {
                req = new XMLHttpRequest();
         }   
         return req;
}

/*function checkVerification()
{       
	
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	if(txtCB_Month>3){
		document.getElementById("areaId").style.display="block"; 
	
		 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;		 
         url="../../../../../TDA_Raised_Create?command=check_TB&txtUnitId="+cmbAcc_UnitCode;
        // alert(url);
         req=getTransport();
         req.open("GET",url,true);        
         req.onreadystatechange=function()
         {        	  
                TDA_Raised_ServletResponse(req);
         }   
         req.send(null);  
	}
	else
	{
		document.getElementById("areaId").style.display="none"; 
	}
        
}*/


/*function TDA_Raised_ServletResponse(req)
{
		 
                 if(req.readyState==4)
		 {
                if(req.status==200)
                {  
                        var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                      //  alert(baseResponse);
                        var tagcommand=baseResponse.getElementsByTagName("command")[0];
                        var Command=tagcommand.firstChild.nodeValue;                                  
                        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                        if(Command=="check_TB")
                        {                                       
                             
                               if(flag=="success")
                               {                                      
                            	   var march=baseResponse.getElementsByTagName("march")[0].firstChild.nodeValue;
                            	   var supp=baseResponse.getElementsByTagName("supp")[0].firstChild.nodeValue;
                            	   var april=baseResponse.getElementsByTagName("april")[0].firstChild.nodeValue;
                            	   if(march=="NoDatainMarch")
                            	   {
                            		   alert("Please Verify Regular March TDA_TCA Verify Menu under MonthEndOperations(Regular) and Then Freeze");
                            	   }
                            	   else if(march=="NotTally")
                            	   {
                            		   alert("Regular March TDA_TCA is Not Tallied in Menu under MonthEndOperations(Regular)");
                            	   }
                            	   
                            	   
                            	   if(supp=="NoDatainsupp")
                            	   {
                            		   alert("Please Verify Supplement March TDA_TCA Verify Menu under MonthEndOperations(Regular) and Then Freeze");
                            	   }
                            	   else  if(supp=="NotTally")
                            	   {
                            		   alert(" Supplement March TDA_TCA is Not Tallied in Menu under MonthEndOperations(Regular)");
                            	   }
                            	   
                            	   
                            	   if(april=="NoDatainApril")
                            	   {
                            		   alert("Please Verify Regular April TDA_TCA Menu under MonthEndOperations(Regular) and Then Freeze");
                            	   }
                            	   else  if(april=="NotTally")
                            	   {
                            		   alert("Regular April TDA_TCA is Not Tallied in Menu under MonthEndOperations(Regular)");
                            	   }
                            	   
                            	   
                               }
                               else
                               {                                                   
                                       alert("Please Verify Regular March-2012,Supplement March-2012 and Regular April-2012 TDA/TCA Menu under MonthEndOperations(Regular) and Then Freeze ");
                               }
                       }
              }
		 }    
}*/

/**
 *  Numbers only Checking 
 */

function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
   
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48||unicode>57 ) 
            return false ;
    }
 }
 
 
 
 /**
  *   Confirmation before Submiting Form 
  */
    
 
 function confirmation()
 {
 //alert("enter....");
  
  checknull();
   
  var req=getTransport();
  var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
  var cmbOffice_code=document.getElementById("cmbOffice_code").value;
  var fin_year=document.getElementById("fin_year").value;
  /*var txtCB_Year=document.getElementById("txtCB_Year").value;
  var txtCB_Month=document.getElementById("txtCB_Month").value;*/
  var url="../../../../../FreezeA52_Verification?command=confim&cmbAcc_UnitCode="+cmbAcc_UnitCode+
  "&cmbOffice_code="+cmbOffice_code+"&fin_year="+fin_year; //"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
  //alert(url);
  req.open("POST",url,true);
  req.onreadystatechange=function()
         {        	  
       if(req.readyState==4)
		 {
         if(req.status==200)
                {
                //alert(req.responseText);
                var baseResponse=req.responseXML.getElementsByTagName("response")[0];
               // alert(baseResponse);
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="success")
                { 
                  alert("Submited Successfully");
                }else if(flag=="already")
                { 
                    alert("Already Frozen");
                  }
                
                }
                }
                };
            req.send(null);     
 }
 