//alert("js");
/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////
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

var window_BankAccNumber;
function ListAll(){    
     if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed){
       window_BankAccNumber.resizeTo(500,500);
       window_BankAccNumber.moveTo(250,250); 
       window_BankAccNumber.focus();
    }
    else
    {
        window_BankAccNumber=null;
    }
         var cmbAcc_UnitCode=document.frmNum_OB_Details.cmbAcc_UnitCode.value;
         var cmbOffice_code=document.frmNum_OB_Details.cmbOffice_code.value;
         var financial_year = document.getElementById("cmbFinancialYear").value;
        // var cmbassetvehicle=document.getElementById("cmbassetvehicle").value;
         window_BankAccNumber= window.open("Assets_Rigs_Vehicle_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&financial_year="+financial_year,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
    }

window.onunload=function()
{
if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
};


function doParentBankAccNumbers(VehicleDesc,RigId,VehicleId,RigDesc)
{
   //alert("inside parent");
        // alert(majorclass);
         
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            var d2=document.getElementById("cmdDelete");
            d2.style.display="block";
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";  
           
            document.getElementById("cmbassetrigs").options[document.getElementById("cmbassetrigs").selectedIndex].text=RigDesc;
           
            document.getElementById("cmbassetvehicle").options[document.getElementById("cmbassetvehicle").selectedIndex].text=VehicleDesc;
            document.getElementById("assetRig").value=RigId;
  
}
function doFunction(Command,param)
{   
//alert("inside dofunction");
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    var financial_year = document.getElementById("cmbFinancialYear").value;
   var cmbassetvehicle=document.getElementById("cmbassetvehicle").value;
    var cmbassetrigs=document.getElementById("cmbassetrigs").value;
    
   
         if(Command=="Add")
        {  
        
                var url="../../../../../Assets_Rigs_Vehicles?Command=Add&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&financial_year="+financial_year+"&cmbassetrigs="+cmbassetrigs+"&cmbassetvehicle="+cmbassetvehicle;
            //   alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                };   
                        req.send(null);
              
        }
        else if(Command=="Delete"){
        	var assetRig=document.getElementById('assetRig').value;
            if(confirm("Do You Really want to Cancel it?")){
                  var url="../../../../../Assets_Rigs_Vehicles?Command=Delete&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&financial_year="+financial_year+
                        "&cmbassetrigs="+cmbassetrigs+"&cmbassetvehicle="+cmbassetvehicle+"&assetRig="+assetRig;
                  // alert(url);
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    };
                            req.send(null);
               
            }
           
       }
       else if(Command=="Update")
       { 
    	  // alert("inside update");
           
                var url="../../../../../Assets_Rigs_Vehicles?Command=Update&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&financial_year="+financial_year+"&cmbassetrigs="+cmbassetrigs+"&cmbassetvehicle="+cmbassetvehicle;
              //  alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                };   
                        req.send(null);
             
       }
       
   
}


/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function handleResponse(req)
{ 
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
        
            if(Command=="Add")
            {
                addRow(baseResponse);
            }
            else if(Command=="Delete")
            {
                deleteRow(baseResponse);
            }
            
            else if(Command=="Update")
            {
                UpdateRow(baseResponse);
            }
        }
    }
}


function addRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   // alert(flag);
    if(flag=="success")
    {
        alert("Record inserted into database");
        ClearAll();
    }
    else if(flag="failure")
    {
        alert("Record not inserted into database");
    }
    if(flag=="AlreadyExist")
            {
                alert("Record Already Exist");
                 ClearAll();
                
                
                
            }
}

function deleteRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
        alert("Records Cancel");
         ClearAll();
    }
    else
    {
        alert("Record not Cancel");
    }
}  

function UpdateRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var items=new Array();
    
    if(flag=="success")
    {
        alert("Record Updated");
        document.frmNum_OB_Details.cmbassetclass.disabled=false;
        document.frmNum_OB_Details.cmbFinancialYear.disabled=false;
        document.frmNum_OB_Details.txtOffice_Name.disabled=false;
        
           
        ClearAll();
    }
    else
    {
        alert("Record not Updated");
    }
}

function ClearAll(){        
        document.getElementById("cmbAcc_UnitCode").selectedIndex=0;
        document.getElementById("cmbOffice_code").selectedIndex=0;
        document.getElementById("cmbFinancialYear").selectedIndex=0;
        document.getElementById("cmbassetrigs").selectedIndex=0;
        document.getElementById("cmbassetvehicle").selectedIndex=0;         
        
}


function nullcheck()
{
   
            var accounting_unit_id=document.frmNum_OB_Details.cmbAcc_UnitCode;
		if(accounting_unit_id.value=="")
		{ 
		     alert("Please Select the 'Accounting Unit'");
		     accounting_unit_id.focus();
		     return false;
		}

		var accounting_unit_office_id = document.frmNum_OB_Details.cmbOffice_code;
		if(accounting_unit_office_id.value=="")
		{ 
		     alert("Please Select the 'Accounting Office'");
		     accounting_unit_office_id.focus();
		     return false;
		}

	
		var financial_year = document.frmNum_OB_Details.cmbFinancialYear;
		if(financial_year.value=="")
		{ 
		     alert("Please select the 'Financial Year'");
		     financial_year.focus();
		     return false;
		}
          
   
}

function exit()
{
        self.close();

}

