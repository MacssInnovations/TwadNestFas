var BillMajorCode;
var bill_minr_code;
var acc_unit_id;
var acc_unit_off_id;
var desig_sel_code;
var desig_sel_desc;
var seq=0;
var pagesize = 10;
var response="";
var com_id;var emp_flag;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
 alert("Request object created:::::"+req);
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function check_leng(param,val)
{	 
		if((val.length)>=190)
		{
			  if(param=='remarks')			  
				  	   alert("Please Enter Remarks below 200 characters");			           			  
			  else			  
				  	   alert("Please Enter Paticulars below 200 characters");				  	  
			  
		}
		
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function callmeth1(){
	
	var cmbAcc_UnitCode=document.getElementById('cmbAcc_UnitCode').value;
	 var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	url="../../../../../sanction_proceed_approval?command=loadsancProno&cmbOffice_code="+cmbOffice_code+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
    var req=getTransport();
    req.open("POST",url,true);   
    req.onreadystatechange=function(){
    	getReqId1(req);
     };   
     req.send(null);

}