                                        /*
                                            LoadAccountingUnitID('BOTH_UNITS_AND_OFFICES')
                                            LoadAccountingUnitID('ONLY_UNITS')
                                            LoadAccountingUnitID('LIST_ALL_UNITS')
                                            LoadAccountingUnitID('LIST_ALL_UNITS_ONLY')
                                            
                                            LoadAccountingUnitID('FOR_LIST_0')      // 0 -- Accounting Units Only 
                                            LoadAccountingUnitID('FOR_LIST_1')      // 1 -- Both Units and Offices 
                                            
                                            LoadAccountingUnitID('FOR_LIST_RD')      // RD -- Both old(Re deployed) office and new offices for viewing reports purpose only
                                                  
                                            
                                        */

var command_for_office="";

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
        var url="../../../../../../Load_Accounting_Unit_ID.kv?COMMAND="+COMMAND;
       // alert("COMMAND***********"+COMMAND);
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
          handle_loadAccountingUnitID(req);
        }        
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
            	common_LoadOffice();
            }
            else
            {
               //alert("else part");
               common_LoadOffice();            
            }         
            
            
        }
        else
        {
          alert("Failed to Load Accounting Unit");
        }
                 
     }
    }
}
