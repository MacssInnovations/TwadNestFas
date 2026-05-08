//	Freeze A52 Register Value	//
var seq = 0;
var seq1 = 1;
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

function handleResponse(req)
{  
     
    if(req.readyState==4)
    { 
        if(req.status==200)
        {  

            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
          
           if(Command=="verfiedA52AA52Register")
            {
            
                addRow(baseResponse);
            }
           /*else if(Command=="checkStatusA52AA52Register"){
        	   checkStatusA52AA52Register1(baseResponse);
           }*/
           else if(Command=="UnFreezeA52Value"){
        	   UnFreezeA52Value1(baseResponse);
           }
           
         }
    }
}
/*function checkStatusA52AA52Register1(baseResponse){
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	var count = baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
	if(flag=="success"){
			var A52_STATUS = baseResponse.getElementsByTagName("A52_STATUS")[0].firstChild.nodeValue;
			var AA52_STATUS = baseResponse.getElementsByTagName("AA52_STATUS")[0].firstChild.nodeValue;
     //alert(A52_STATUS+"--------"+AA52_STATUS);
      if((A52_STATUS=='Y')&&(AA52_STATUS=='N')){
    	  document.frmVerify_A52_AA52_Register.a52_aa52_option.disabled=true;
    	 // document.frmVerify_A52_AA52_Register.a52_aa52_option[1].disabled=false;
    	 // document.frmVerify_A52_AA52_Register.a52_aa52_option[1].checked=true;	 
      }else if(((A52_STATUS=='N'))&&(AA52_STATUS=='Y')){
    	  document.frmVerify_A52_AA52_Register.a52_aa52_option.disabled=false;
    	 // document.frmVerify_A52_AA52_Register.a52_aa52_option[1].disabled=true;
    	  document.frmVerify_A52_AA52_Register.a52_aa52_option.checked=true;
      }else if(((A52_STATUS=='Y'))&&(AA52_STATUS=='Y')){
    	//  alert("Both are verified");
    	  document.frmVerify_A52_AA52_Register.a52_aa52_option.disabled=true;
    	//  document.frmVerify_A52_AA52_Register.a52_aa52_option[1].disabled=true;	  
      }

        }
        else
        {
        alert("No Record Exist");
        }
}
*/
function doFunction(Command,param)
{  
	//alert(" VerifyA52AA52   ");
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
    var verifya52aa52;
       
        if(Command=="VerifyA52AA52")
        {
           if(nullcheck())
            {
        	   if(document.frmVerify_A52_AA52_Register.a52_aa52_option.checked==true)
        	    {
        	    	verifya52aa52=document.getElementById("a52_aa52_option").value;
        	    }
                var url="../../../../../Verify_A52_AA52_Register?command=verfiyA52AA52Register"+
                	"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"" +
                	"&cmbFinancialYear="+cmbFinancialYear+
                	"&verifya52aa52="+verifya52aa52;
                //alert("url "+url);
                var req=getTransport();
                req.open("GET",url,true);         
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                };   
                        req.send(null);
           }
    }else  if(Command=="UnFreezeA52Value")
    {
    	//alert("un  FreezeA52value   ");
        if(nullcheck())
         {
     	   if(document.frmVerify_A52_AA52_Register.a52_aa52_option.checked==true)
     	    {
     	    	verifya52aa52=document.getElementById("a52_aa52_option").value;
     	    }
             var url="../../../../../Verify_A52_AA52_Register?command=UnFreezeA52Value"+
             	"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"" +
             	"&cmbFinancialYear="+cmbFinancialYear+
             	"&verifya52aa52="+verifya52aa52;
            // alert("url "+url);
             var req=getTransport();
             req.open("GET",url,true);         
             req.onreadystatechange=function()
             {
                handleResponse(req);
             };   
                     req.send(null);
        }
 }
        
}
/*function checkStatus(){
	document.frmVerify_A52_AA52_Register.a52_aa52_option.checked=false;
   // document.frmVerify_A52_AA52_Register.a52_aa52_option[1].checked=false;
    document.frmVerify_A52_AA52_Register.a52_aa52_option.disabled=false;
  //  document.frmVerify_A52_AA52_Register.a52_aa52_option[1].disabled=false;
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	 if(document.frmVerify_A52_AA52_Register.cmbFinancialYear.value.length==0 )
	    {
	        alert("select finanical year");
	        return false;
	    } 
	else{
    var url="../../../../../Verify_A52_AA52_Register?command=checkStatusA52AA52Register"+
	"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
	"&cmbFinancialYear="+cmbFinancialYear;
		//alert("url "+url);
		var req=getTransport();
		req.open("GET",url,true);         
		req.onreadystatechange=function()
		{
		   handleResponse(req);
		};   
		        req.send(null);
	}
}*/

function nullcheck()
{
  
    if(document.frmVerify_A52_AA52_Register.cmbFinancialYear.value.length==0 )
    {
        alert("select finanical year");
        return false;
    }  
    if((document.frmVerify_A52_AA52_Register.a52_aa52_option.checked==false) )//&& (document.frmVerify_A52_AA52_Register.a52_aa52_option[1].checked==false)
    {
        alert("Select Register Option"); 
        return false;
    }   
    return true; 
}
function addRow(baseResponse)
{
 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {   
         alert("successfully Freeze A52 Value");
         clearall();
        
    }
    else if(flag=="someOfficeNot"){
    	alert("Do Qty Freeze for all the units in your control");
    }
    else if(flag=="NotCircle"){
    	 alert("Circle Only Use this freeze ");
    }
    else if(flag=="failure")
    {
        alert("Records not inserted");
    }
    else if(flag=="AlreadyExist")
    {
        alert("Already Freeze Done");
        clearall();
        
    }
}
function UnFreezeA52Value1(baseResponse)
{
 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {   
         alert("successfully UnFreeze A52 Value");
         clearall();
        
    }else  if(flag=="A52success")
    {   
        alert("successfully UnFreeze A52 Value");
        clearall();
       
   } else if(flag=="Circle_Freezed"){
  	 alert("Circle Only Use this Unfreeze ");
   } else if(flag=="UnFreezed"){
  	 alert("You didnt Freeze,First Freeze it ");
   }
    
    else if(flag=="NotCircle"){
    	 alert("Circle Only Use this Unfreeze ");
    }
    else if(flag=="failure")
    {
        alert("Records not inserted");
    }
   
    else if(flag=="FreezeFirst")
    {
        alert("You didnt Freeze,First Freeze it");
        clearall();
        
    }
}

function clearall()
{
       document.frmVerify_A52_AA52_Register.a52_aa52_option.checked=false;
       //document.frmVerify_A52_AA52_Register.a52_aa52_option[1].checked=false;
       document.frmVerify_A52_AA52_Register.cmbFinancialYear.value="2011-12";
       document.frmVerify_A52_AA52_Register.a52_aa52_option.disabled=false;
       //document.frmVerify_A52_AA52_Register.a52_aa52_option[1].disabled=false;
       LoadAccountingUnitID('LIST_ALL_UNITS');
      // setTimeout('checkStatus()',1000);
      
}