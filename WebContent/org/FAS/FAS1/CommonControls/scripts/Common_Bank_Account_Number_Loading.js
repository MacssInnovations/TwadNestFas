/** Browser Identification */

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



var bank_ac_no =new Array();
var acc_desc   =new Array();
var bank_name  = new Array();
var branch_name = new Array();
var bank_id  = new Array();
var branch_id = new Array();
var opr_mode  = new Array();



/** Main Function */
function LoadBankAccountNumber()
{ 
	   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	 
	//   alert("cmbAcc_UnitCode:::"+cmbAcc_UnitCode);
           var url;
        
         
            url="../../../../../Common_Bank_Account_Number_Loading.kv?command=LoadBankAccountNumber&cmbAcc_UnitCode="+cmbAcc_UnitCode;
	   
    
           if(document.getElementById("txtOprCode"))
	   {
		 	var cashbook_yr=document.getElementById("txtCB_Year").value;	 
		 	var cashbook_mn=document.getElementById("txtCB_Month").value;	 
		 	url+="&option=nontwad";
	   }
           
            //alert(url);
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




function Bank_Branch_Name()
{

    accnumber = document.getElementById("cmbBankAccNo").value;    
   // alert(accnumber);
        
    for(var k=0;k<bank_ac_no.length;k++)
    {
		if ( bank_ac_no[k]== accnumber  )
		{
			/* Display Bank Name */
			document.getElementById("txtBankName").value =bank_name[k];
			
			/* Display Branch Name */
			if ( branch_name[k]!='null')
			{
			  document.getElementById("txtBranchName").value =branch_name[k];
			}
			/* Display Bank ID */			
			document.getElementById("txtBankID").value =bank_id[k];
			
			/* Branch ID */			
			document.getElementById("txtBranchID").value =branch_id[k];
			
			/* Operation Mode */			
			document.getElementById("txtOprMode").value =opr_mode[k];			
			if(document.getElementById("txtOprCode"))
			{
					var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
					var url="../../../../../Common_Bank_Account_Number_Loading.kv?command=LoadOprCode&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&bank_id="+bank_id[k]+"&branch_id="+branch_id[k]+"&opr_mode="+opr_mode[k]+"&acc_no="+bank_ac_no[k];					
					var req=getTransport();
				    req.open("GET",url,true); 
				    req.onreadystatechange=function()
				    {
				    	 	LoadBankAccountNumberRes(req);
				    }   
				    req.send(null);
			}
		}
		
    }
	 
}


function Bank_Branch_Namee1(accno)
{  
	   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	 
	   var url="../../../../../Common_Bank_Account_Number_Loading.kv?command=Bank_Branch_Name&cmbAcc_UnitCode="+cmbAcc_UnitCode + "&accno="+accno+"&bank_id="+0+"&branch_id="+0+"&acc_no="+0+"&opr_mode="+0;
	   //alert(url);
	   if(document.getElementById("txtOprCode"))
	   {
		 	var cashbook_yr=document.getElementById("txtCB_Year").value;	 
		 	var cashbook_mn=document.getElementById("txtCB_Month").value;	 
		 	url+="&option=nontwad";
	   }
	   var req=getTransport();
	   req.open("GET",url,true); 
	   req.onreadystatechange=function()
	   {
		   manipulate3(req);
	   }   
       req.send(null);
	  
}


function manipulate3(xmlrequest) {
	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			if (command == "Bank_Branch_Name") {
				Bank_Branch_Name1(baseResponse);
			}
		}
	}
}





function Bank_Branch_Name1(baseResponse)
{
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="success")
    {
		    var bank_idd = baseResponse.getElementsByTagName("bank_id")[0].firstChild.nodeValue;
		    var branch_idd = baseResponse.getElementsByTagName("branch_id")[0].firstChild.nodeValue;
		    var opr_modee = baseResponse.getElementsByTagName("opr_mode")[0].firstChild.nodeValue;			

		    if(bank_idd == 'null' || bank_idd == '0' )
        	{
		    	bank_idd = "";
        	}
		    
		    if(branch_idd == 'null' || branch_idd == '0' )
        	{
		    	branch_idd = "";
        	}
		    
		    if(opr_modee == 'null' || opr_modee == '0' )
        	{
		    	opr_modee = "";
        	}
		    
			document.getElementById("txtBankID").value =bank_idd;
			document.getElementById("txtBranchID").value =branch_idd;
			document.getElementById("txtOprMode").value =opr_modee;

			
		}
}
function Bank_Branch_Name_Loading(accno)
{
	   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	 
	   var url="../../../../../Common_Bank_Account_Number_Loading.kv?command=Bank_Branch_Name&cmbAcc_UnitCode="+cmbAcc_UnitCode + "&accno="+accno;
	   //alert(url);
	   if(document.getElementById("txtOprCode"))
	   {
		 	var cashbook_yr=document.getElementById("txtCB_Year").value;	 
		 	var cashbook_mn=document.getElementById("txtCB_Month").value;	 
		 	url+="&option=nontwad";
	   }
	   var req=getTransport();
	   req.open("GET",url,true); 
	   req.onreadystatechange=function()
	   {
		   manipulate1(req);
	   }   
       req.send(null);
	  
}


function Bank_Branch_Name_Loading1(baseResponse)
{
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert(flag);
	if(flag=="success")
    {
		    var bank_idd = baseResponse.getElementsByTagName("bank_id")[0].firstChild.nodeValue;
		    var branch_idd = baseResponse.getElementsByTagName("branch_id")[0].firstChild.nodeValue;
		    var bank_name = baseResponse.getElementsByTagName("bank_name")[0].firstChild.nodeValue;
		    var branch_name = baseResponse.getElementsByTagName("branch_name")[0].firstChild.nodeValue;
		    var opr_modee = baseResponse.getElementsByTagName("opr_mode")[0].firstChild.nodeValue;			

		    if(bank_idd == 'null' || bank_idd == '0' )
        	{
		    	bank_idd = "";
        	}
		    
		    if(branch_idd == 'null' || branch_idd == '0' )
        	{
		    	branch_idd = "";
        	}
		    
		    if(opr_modee == 'null' || opr_modee == '0' )
        	{
		    	opr_modee = "";
        	}	
		    
		    if(bank_name == 'null' || bank_name == '0' )
        	{
		    	bank_name = "";
        	}
		    
		    if(branch_name == 'null' || branch_name == '0' )
        	{
		    	branch_name = "";
        	}
		    
			document.getElementById("txtBankID").value =bank_idd;
			document.getElementById("txtBranchID").value =branch_idd;
			document.getElementById("txtOprMode").value =opr_modee;
			document.getElementById("txtBankName").value =bank_name;
			document.getElementById("txtBranchName").value =branch_name;
			document.getElementById("txtOprCode").value =opr_modee;
			
		}
}

function manipulate1(xmlrequest) {
	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			//alert(command);
			if (command == "Bank_Branch_Name") {
				Bank_Branch_Name_Loading1(baseResponse);
			}
		}
	}
}