/** To handle Errors **/
onerror = handleErr;
var txt="";
function handleErr(msg,url,l)
{
	txt="There was an error on this page.\n\n ";
	txt+="Error: " + msg + "\n";
	txt+="URL: " + url + "\n";
	txt+="Line: " + l + "\n\n";
	txt+="Click OK to continue.\n\n";
	alert(txt);
	return true;
}

/** To create javascript request object **/
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

 function Exit()
 {
    self.close();
 }
function checkStatus()
{
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code=document.getElementById("cmbOffice_code").value;
		var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	    var verifyassetsnum;
	 
	    	verifyassetsnum=document.getElementById("assets_num_option").value;
	var url;
	   if(nullcheck1())
     {
  	url="../../../../../Verify_Assets_Numerical_AC_OB_Status?command=checkStatus"+
  	"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+" " +
  	"&cmbFinancialYear="+cmbFinancialYear;
  	alert("checkStatus "+url);
		var req=getTransport();
      req.open("GET",url,true);  
      req.onreadystatechange=function(){
         processResponse(req);
      };   
      req.send(null);
     }
  }
//******************************************** CallServer Coding *******************//
function callServer(command){  
          /* var accounting_unit_id=document.frmAssetsNumericalEdit.cmbAcc_UnitCode.value;
	   var accounting_unit_office_id = document.frmAssetsNumericalEdit.cmbOffice_code.value;
	   var assetmajor=document.frmAssetsNumericalEdit.cmbmajorasset.value;
	   var financial_year = document.frmAssetsNumericalEdit.cmbFinancialYear.value;  */
	   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code=document.getElementById("cmbOffice_code").value;
		var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	    var verifyassetsnum;
	    /*if(document.frmVerify_Assets_Numerical_AC_OB_Status.assets_num_option[0].checked==true)
	    {*/
	    	verifyassetsnum=document.getElementById("assets_num_option").value;
	    //}
        
       var url="";

        if(command=="AddVerifyAssetsNumericalACOBStatus")
        {  
        	if(nullcheck())
            {
        	//if(checkStatus()){

        	 var url="../../../../../Verify_Assets_Numerical_AC_OB_Status?command=AddVerifyAssetsNumericalACOBStatus"+
            	"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+" " +
            	"&cmbFinancialYear="+cmbFinancialYear+
            	"&verifyassetsnum="+verifyassetsnum;
            	alert("AddVerifyAssetsNumericalACOBStatus "+url);
        			var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    };   
                    req.send(null);
        	//}
            }
        }
      /* else if(command=="checkStatus"){
    	   if(nullcheck1())
           {
        	url="../../../../../Verify_Assets_Numerical_AC_OB_Status?command=checkStatus"+
        	"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+" " +
        	"&cmbFinancialYear="+cmbFinancialYear;
        	//alert("checkStatus "+url);
    		var req=getTransport();
            req.open("GET",url,true);  
            req.onreadystatechange=function(){
               processResponse(req);
            };   
            req.send(null);
           }
        }*/
}  


//********************************* CallServer Response Coding ***************************************//

function processResponse(req)
{   
  if(req.readyState==4)
  {
      if(req.status==200)
      {   

    	  var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          
          var tagCommand=baseResponse.getElementsByTagName("command")[0];
          
          var command=tagCommand.firstChild.nodeValue; 
          alert("command=="+command);
    	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
    	       
               if(command=="AddVerifyAssetsNumericalACOBStatus")
              {
            	 
            	  if(flag=="success")
            	  {
            		  alert("Records Inserted Successfully.");
            		  //clearAll();
            	  }
            	  else if(flag=="AlreadyExist")
                  {
            		  alert("You already verify Assets Numerical AC ,So you cant Verify Again"); 
  
                  }
            	  else if(flag=="NotInsert")
                  {
            		  alert("NotInsert Record");
                  } 
            	  
            	  else{
                	  alert("Exception");
                  }
              }
              else if(command=="checkStatus")
               {
             
             	  if(flag=="success")
             	  {
             		 var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
             		// alert("exists"+exists);
             		  if(exists=="Yes"){
             			  
             			 var accunitid=baseResponse.getElementsByTagName("accounting_unit_id")[0].firstChild.nodeValue;
             			 var accoffid=baseResponse.getElementsByTagName("accounting_for_office_id")[0].firstChild.nodeValue;
             			 var numstatus=baseResponse.getElementsByTagName("NUM_OB_STATUS")[0].firstChild.nodeValue;
             			// alert("a52status "+a52status);
             			 if(numstatus=="Y"){
             				alert("You already verify Assets Numerical AC ,So you cant Verify Again"); 
             				//return false;
             				//clearAll();
             			 } 
             		  }else {
             			  alert("Not verify");
             			// callServer('AddVerifyAssetsNumericalACOBStatus','null');
             			 // return true;
             		  }
             	  }else{
             		// callServer('AddVerifyAssetsNumericalACOBStatus','null');
             		 // return true;
             	  }
             	  
               }       
    	  }
     }
}
function nullcheck()
{
	 if(document.frmVerify_Assets_Numerical_AC_OB_Status.cmbAcc_UnitCode.value.length==0 )
	    {
	        alert("select Accounting Unit Id  year");
	        return false;
	    } 
	 else  if(document.frmVerify_Assets_Numerical_AC_OB_Status.cmbOffice_code.value.length==0 )
	    {
	        alert("select Accounting For Office Id year");
	        return false;
	    }     
		 else if(document.frmVerify_Assets_Numerical_AC_OB_Status.cmbFinancialYear.value.length==0 )
	    {
	        alert("select finanical year");
	        return false;
	    }  
	    else if(!document.getElementById('assets_num_option').checked)
	    {
	        alert("Select Verify Option"); 
	        return false;
	    } 
    return true; 
}
function nullcheck1()
{
	 if(document.frmVerify_Assets_Numerical_AC_OB_Status.cmbAcc_UnitCode.value.length==0 )
	    {
	        alert("select Accounting Unit Id  year");
	        return false;
	    } 
	 else  if(document.frmVerify_Assets_Numerical_AC_OB_Status.cmbOffice_code.value.length==0 )
	    {
	        alert("select Accounting For Office Id year");
	        return false;
	    }     
		 else if(document.frmVerify_Assets_Numerical_AC_OB_Status.cmbFinancialYear.value.length==0)
	    {
	        alert("select finanical year");
	        return false;
	    }  
	      
    return true; 
}