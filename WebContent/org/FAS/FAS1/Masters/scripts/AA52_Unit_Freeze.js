//	Freeze AA52 unit//
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

function LoadAccountingUnitID(COMMAND)
{
        command_for_office = COMMAND;
        var url="../../../../../Load_Accounting_Unit_ID.kv?COMMAND="+COMMAND;
        //alert("command_for_office&&&&&&&&"+url+"ssssss"+command_for_office);
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
          handle_loadAccountingUnitID(req);
        }  ;      
        req.send(null);
    
}


function handle_loadAccountingUnitID(req)
{
   
    if(req.readyState==4)
    {
   
     if(req.status==200)
     {
   
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
        
       var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        
   
      
        if(flag=="success")
        { 
            var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode");         
                cmbAcc_UnitCode.length=0;
          
            var option_count=baseresponse.getElementsByTagName("option");                       
            var root = null;
            for(var i=0;i<option_count.length;i++)
            {  
                var option=document.createElement("OPTION");
                root = baseresponse.getElementsByTagName("option")[i];
                var accounting_unit_id=root.getElementsByTagName("accounting_unit_id")[0].firstChild.nodeValue;
                
                var accounting_unit_name=root.getElementsByTagName("accounting_unit_name")[0].firstChild.nodeValue;
                
                option.text=accounting_unit_name+"("+accounting_unit_id+")";
                option.value=accounting_unit_id;
                try
                {   
                    cmbAcc_UnitCode.add(option);
                }
                catch(errorObject)
                {
                    cmbAcc_UnitCode.add(option,null);
                }   
            }            
                       
        
            /** Load Accounting Office ID */ 
            if ( (command_for_office == "ONLY_UNITS") || (command_for_office=="LIST_ALL_UNITS_ONLY") || (command_for_office=="FOR_LIST_0" ) )
            {
            
            }
            else
            {
            	common_LoadOfficeCode();            
            }         
            
            
        }
        else
        {
          alert("Failed to Load Accounting Unit");
        }
                 
     }
    }
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
          
           if(Command=="FreezeAA52Unit")
            {
            
                addRow(baseResponse);
            }
          /* else if(Command=="checkStatusA52AA52Register"){
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
     //alert(A52_STATUS+"--------"+AA52_STATUS);
      if((A52_STATUS=='Y')){
    	  document.frmFreeze_AA52Register.aa52_option.disabled=true; 
      }

        }
        else
        {
       // alert("No Record Exist");
        }
}*/

function doFunction(Command,param)
{  
	//alert(" FreezeAA52Unit   ");
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
    var verifya52aa52;
       
        if(Command=="FreezeAA52Unit")
        {
           if(nullcheck())
            {
        	   if(document.frmFreeze_AA52Register.aa52_option.checked==true)
        	    {
        	    	verifya52aa52=document.getElementById("aa52_option").value;
        	    }
                var url="../../../../../AA52_Unit_Freeze?command=FreezeAA52Unit"+
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
	document.frmFreeze_AA52Register.aa52_option.checked=false;
    document.frmFreeze_AA52Register.aa52_option.disabled=false;
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	 if(document.frmFreeze_AA52Register.cmbFinancialYear.value.length==0 )
	    {
	        alert("select finanical year");
	        return false;
	    } 
	else{
    var url="../../../../../AA52_Unit_Freeze?command=checkStatusA52AA52Register"+
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
}
*/
function nullcheck()
{
  
    if(document.frmFreeze_AA52Register.cmbFinancialYear.value.length==0 )
    {
        alert("select finanical year");
        return false;
    }  
    if((document.frmFreeze_AA52Register.aa52_option.checked==false) )//&& (document.frmFreeze_AA52Register.aa52_option[1].checked==false)
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
         alert("successfully Freeze AA52 Unit Level Done");
         clearall();
        
    } 
    else if(flag=="failure")
    {
        alert("Not Freezed");
    }
    else if(flag=="AlreadyExist")
    {
        alert("Already Freezed Done");
        clearall();
        
    }
}
function common_LoadOfficeCode()
{
	
    var unitID_val=document.getElementById("cmbAcc_UnitCode").value;     
  // alert("unitID_val"+unitID_val);
    if(unitID_val!="")
    {
    	
    	//alert("unit id ");
        var cmbAcc_UnitCode=unitID_val;
        var url="../../../../../Freeze_Qty_A52?command=LoadUnitWise_OfficeCode&cmbAcc_UnitCode="+cmbAcc_UnitCode;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            handle_loadOffice_oly1(req);
        };
        req.send(null);
    }     
}

function handle_loadOffice_oly1(req)
{
  
    if(req.readyState==4)
    {
     
     if(req.status==200)
     {
      
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
        if(flag=="success")
        { 
         
         try
         {
            var cmboffice=document.getElementById("cmbOffice_code");
           
            cmboffice.innerHTML="";
            var offidvalues=baseresponse.getElementsByTagName("offid");
       
            for(i=0;i<offidvalues.length;i++)
            {  
                var option=document.createElement("OPTION");
                var offid=baseresponse.getElementsByTagName("offid")[i].firstChild.nodeValue;
                var uuid=baseresponse.getElementsByTagName("uuid")[i].firstChild.nodeValue;
                var offname=baseresponse.getElementsByTagName("offname")[i].firstChild.nodeValue;
                option.text=offname+"("+offid+")"+"("+uuid+")";
                option.value=offid;
                try
                {
                    cmboffice.add(option);
                }
                catch(errorObject )
                {
                    cmboffice.add(option,null);
                }   
            }
            
         }
         catch(err)
         {
            alert("Problem in Loading Office code ");
         }
            
        }
        else
        {
          
         try
         {
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--select office--";
            option.value="";
            try
            {
                cmboffice.add(option);
            }
            catch(errorObject )
            {
                cmboffice.add(option,null);
            }
         }
         catch(err)
         {
            alert("Problem in Loading Office code ");
         }         
            
            
        }
            
             
     }
    }
}
function clearall()
{
       document.frmFreeze_AA52Register.aa52_option.checked=false;
       document.frmFreeze_AA52Register.cmbFinancialYear.value="2011-12";
       document.frmFreeze_AA52Register.aa52_option.disabled=false;
       LoadAccountingUnitID('LIST_ALL_UNITS');
       common_LoadOfficeCode();
}