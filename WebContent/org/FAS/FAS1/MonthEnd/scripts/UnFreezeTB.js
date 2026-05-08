function getTransport(){
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

function checkStatus()
{
	alert("checkStatus");
	return false;
}

function loadyear_month()
{

var today= new Date(); 
var day=today.getDate();
var month=today.getMonth();
month=month+1;
var year=today.getYear();
if(year < 1900) year += 1900;

document.frmTrialBalance.txtCB_Year.value=year;
document.frmTrialBalance.txtCB_Month.value=month;
unfreezeAct();

}
function checknull()
{
    if((document.getElementById("txtCB_Year").value.length!=4)|| (document.getElementById("txtCB_Year").value.length<=0) ||(document.getElementById("txtCB_Year").value==""))
    {
        alert("Enter the correct year");
        document.getElementById("txtCB_Year").focus();
        return false;
    }
    if(document.getElementById("txtCB_Month").value=="")
    {
        alert("Select a month");
        return false;
    }
   
}


function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
   
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48||unicode>57 ) 
            return false; 
    }
 }
 
 
 
  /**
  *   Confirmation before Submiting Form 
  */
    
 
 function confirmation() {
 
  /** Call Cehcknull Function */
  checknull();
  
  if(confirm("Are you sure do you want to Freeze Trial Balance ?"))
  {
     var conf=document.getElementById("frmTrialBalance");
     conf.action="../../../../../UnFreezeTB.kv";
     conf.submit();
     return true;
  
  } 
  else 
  {
      return false;
  }  
 
 }
 function unfreezeAct(){
	var cashbookYear=document.getElementById('txtCB_Year').value;
	var cashbookMonth=document.getElementById('txtCB_Month').value;
	//document.getElementById('unitiddiv').style.display="none";
	//document.getElementById('cmbAcc_UnitCode').disabled="true";
	var url="../../../../../TBunfreezeRaised.htm?command=loadaccount&cashbookYear="+cashbookYear+"&cashbookMonth="+cashbookMonth;
	var req=getTransport();
    req.open("POST",url,true);                
    req.onreadystatechange=function(){
    	loadCombo(req);
    };
    req.send(null); 
 }
 function loadCombo(req){
	 if(req.readyState==4){ 
         if(req.status==200){
        	response=req.responseXML.getElementsByTagName("response")[0]; 
        	mainCategoryCombo();
         }
     }
 }
 function mainCategoryCombo(){
	 var len = response.getElementsByTagName("ACCOUNTING_UNIT_ID").length;	
		var select=document.getElementById('cmbAcc_UnitCode');
		if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="success"){		
			//document.getElementById('unitiddiv').style.display="block";
			//alert("not::::");
			//document.getElementById('cmbAcc_UnitCode').disabled="true";
			var listOpt=document.createElement("option");
			select.length=0;
			select.appendChild(listOpt);
			listOpt.text="select";
			listOpt.value="select";	
			for(var i=0; i<len; i++){
				listOpt=document.createElement("option");
				select.appendChild(listOpt);
				listOpt.text=response.getElementsByTagName("accounting_unit_name")[i].firstChild.nodeValue;
				listOpt.value=response.getElementsByTagName("ACCOUNTING_UNIT_ID")[i].firstChild.nodeValue;
			}
			//document.getElementById('cmbAcc_UnitCode').disabled="false";
		}else if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="nodata"){
			select.length=1;
			select.selectedIndex=0;
			alert("No data");
			//document.getElementById('cmbAcc_UnitCode').disabled="true";
		}else{
			select.length=1;
			select.selectedIndex=0;
		}
 }