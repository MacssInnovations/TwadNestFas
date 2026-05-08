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

function Exit(){
    self.close();
 }

function sub_LedgerCode(id)
{
    var SpecificSL=document.getElementById("sl_code");
   if(id=="Specific")
   {
    SpecificSL.style.display="block";
   }
   else
   {
   SpecificSL.style.display="none";
   }
}
function callServer(command) {	
	var url="";
	if(command=="subCode1"){
        var flag=true;
        if(flag==true){
        	var subType=document.getElementById('subLedgerType').value;
        	var cmbOffice_code=document.getElementById('cmbOffice_code').value;
        	var cmbAcc_UnitCode=document.getElementById('cmbAcc_UnitCode').value;
            var fromdate=document.getElementById("txtfromdate").value;
            var todate=document.getElementById("txttodate").value;
           
//        	 	url="../../../../../../DCBReceipt_Reports?command=subCode1&cmbSL_type="+subCode+"&cmbOffice_code="+cmbOffice_code+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&fromdate="+fromdate+
//        	 	"&todate="+todate;
       
        	 	url="../../../../../../DCBReceipt_Reports?command=subCode1&cmbSL_type="+subType+"&cmbOffice_code="+cmbOffice_code+"&cmbAcc_UnitCode="+cmbAcc_UnitCode; 	
        	 	
        	 //	alert("URL--->"+url);
        	 	
        	 	var req=getTransport();
         req.open("GET",url,true);   
         req.onreadystatechange=function(){
        	 loadSubCombo(req);
          };   
          req.send(null);
            
        }
} 
}
function loadSubCombo(req){
	 if(req.readyState==4){ 
        if(req.status==200){
       	 response=req.responseXML.getElementsByTagName("response")[0]; 
       	// alert(req.response);
       	subCategoryCombo(response);
        }
    }
}
function subCategoryCombo(response){

			 var len1 = response.getElementsByTagName("cid").length;	
//	alert(len1);
		var select=document.getElementById('subLedgerCode');
		//alert("Select==>"+select);
		if(response.getElementsByTagName("flag")[0].firstChild.nodeValue=="success"){		
			var listOpt=document.createElement("option");
			select.length=0;
			select.appendChild(listOpt);
			listOpt.text="Select";
			listOpt.value="select";	
			for(var i=0; i<len1; i++){
				listOpt=document.createElement("option");
				select.appendChild(listOpt);
				var cname=response.getElementsByTagName("cname")[i].firstChild.nodeValue;
				var cid=response.getElementsByTagName("cid")[i].firstChild.nodeValue;
				
				
				listOpt.text=cname+"("+cid+")";
				listOpt.value=cid;
			}
		}else{
			select.length=1;
			select.selectedIndex=0;
			alert("No Data");
		}
		
	 
 }
 
function clear_subcode()
{
	document.getElementById('subLedgerCode').length=0;
	document.subLedgertypewise.SpecificSL[0].checked=true;
	document.getElementById("sl_code").style.display="none";

}


function nullCheck()
{

    if((document.frmSubLedgerPartywise.cmbOffice_code.value=="") || (document.frmSubLedgerPartywise.cmbOffice_code.value.length<=0) || (document.frmSubLedgerPartywise.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.frmSubLedgerPartywise.cmbOffice_code.focus();
        return false;
    
    }
    
 
 return true;
}



