//alert("	AA52 Register Freeze Circle");
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
          
           if(Command=="FreezeAA52_Circle")
            {
            
                addRow(baseResponse);
            }
           /*else if(Command=="checkStatusA52AA52Register"){
        	   checkStatusA52AA52Register1(baseResponse);
           }*/
            
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
    	  document.frmFreeze_AA52_Register_Circle.aa52_option.disabled=true;
    	 // document.frmFreeze_AA52_Register_Circle.aa52_option[1].disabled=false;
    	 // document.frmFreeze_AA52_Register_Circle.aa52_option[1].checked=true;	 
      }else if(((A52_STATUS=='N'))&&(AA52_STATUS=='Y')){
    	  document.frmFreeze_AA52_Register_Circle.aa52_option.disabled=false;
    	 // document.frmFreeze_AA52_Register_Circle.aa52_option[1].disabled=true;
    	  document.frmFreeze_AA52_Register_Circle.aa52_option.checked=true;
      }else if(((A52_STATUS=='Y'))&&(AA52_STATUS=='Y')){
    	//  alert("Both are verified");
    	  document.frmFreeze_AA52_Register_Circle.aa52_option.disabled=true;
    	//  document.frmFreeze_AA52_Register_Circle.aa52_option[1].disabled=true;	  
      }

        }
        else
        {
        alert("No Record Exist");
        }
}*/

function doFunction(Command,param)
{  
	//alert(" freeze    ");
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
    var verifya52aa52;

        if(Command=="FreezeAA52_Circle")
        {
           if(nullcheck())
            {
        	   if(document.frmFreeze_AA52_Register_Circle.aa52_option.checked==true)
        	    {
        	    	verifya52aa52=document.getElementById("aa52_option").value;
        	    }
                var url="../../../../../AA52_Circle_Freeze?command=FreezeAA52_Circle"+
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
    }
      
}
/*function checkStatus(){
	document.frmFreeze_AA52_Register_Circle.aa52_option.checked=false;
   // document.frmFreeze_AA52_Register_Circle.aa52_option[1].checked=false;
    document.frmFreeze_AA52_Register_Circle.aa52_option.disabled=false;
  //  document.frmFreeze_AA52_Register_Circle.aa52_option[1].disabled=false;
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	 if(document.frmFreeze_AA52_Register_Circle.cmbFinancialYear.value.length==0 )
	    {
	        alert("select finanical year");
	        return false;
	    } 
	else{
    var url="../../../../../AA52_Circle_Freeze?command=checkStatusA52AA52Register"+
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
  
    if(document.frmFreeze_AA52_Register_Circle.cmbFinancialYear.value.length==0 )
    {
        alert("select finanical year");
        return false;
    }  
    if((document.frmFreeze_AA52_Register_Circle.aa52_option.checked==false) )
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
         alert("successfully Freeze AA52 Circle Done");
         clearall();
        
    }
    else if(flag=="someOfficeNot"){
  	  alert("Some office not Freeze AA52 Unit Level ");
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

function clearall()
{
       document.frmFreeze_AA52_Register_Circle.aa52_option.checked=false;
       document.frmFreeze_AA52_Register_Circle.cmbFinancialYear.value="2011-12";
       document.frmFreeze_AA52_Register_Circle.aa52_option.disabled=false;
       LoadAccountingUnitID('LIST_ALL_UNITS');
      
}