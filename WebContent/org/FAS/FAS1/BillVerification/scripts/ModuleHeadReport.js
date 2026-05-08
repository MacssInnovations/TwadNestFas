

function AjaxFunction()
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

function loadAcNo_Off(){
	var val=null;
	var rep_type=document.getElementById("hid").value;
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var url = path+"/ModuleHeadReport?command=getAccNo&OprMode="+val+"&rep_type="+rep_type+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
	//alert('oprgg'+url+path);
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);

	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);
}
function AccNoload(value,path) {
	
	var val=value;
	
	var rep_type=document.getElementById("hid").value;
	var cmbOprMode=document.getElementById("cmbOprMode").value;
	var cmbAcc_UnitCode=0;
	var url = path+"/ModuleHeadReport?command=getAccNo&OprMode="+val+"&rep_type="+rep_type+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOprMode="+cmbOprMode;
	//alert('oprgg'+url+path);
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);

	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);

}


function manipulate(req){

    if(req.readyState==4)
    {
	        if(req.status==200)
	        {  
		            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
		          
		            var Command=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
		            
		          
		            if(Command=="getAccNo")
		            {
		            
		            	LoadModule(baseResponse);
		            }
	        }
    }
}

function LoadModule(baseResponse){
	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	 
	var cmbAccNo= document.getElementById("cmbAccNo");
	cmbAccNo.length=0;  
	if(flag=="success"){
	   var len=baseResponse.getElementsByTagName("bank_ac_no").length;
	 //  alert(flag+len)
	   for(var i=0;i<len;i++){
		   var bank_ac_no=baseResponse.getElementsByTagName("bank_ac_no")[i].firstChild.nodeValue;
		  var sc=document.createElement("option");
		  sc.value=bank_ac_no;
		  sc.text=bank_ac_no;
		  cmbAccNo.appendChild(sc);
	   }
	}if(flag=="failure"){
		alert('No Data');
	}
	
}