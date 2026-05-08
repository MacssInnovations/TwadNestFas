
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

function manipulate(req)
{
	if(req.readyState==4)
	  {
	      if(req.status==200)
	      {   
	    	  var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	          var tagCommand=baseResponse.getElementsByTagName("command")[0];
	          var command=tagCommand.firstChild.nodeValue; 
	       
	          if(command=="getBillMajor"){
	        	  LoadBill_Major(baseResponse);
	          }
	          if(command=="getBillMinor"){
	        	  LoadBill_Minor(baseResponse); 
	    	  }
	          if(command=="getSubType"){
	        	  LoadSub_Type(baseResponse); 
	    	  }
	    	  }
	      }
	  }
	

function billMajor()
{
		var url =  "../../../../../Fas_GPFSanction_List_Repot?command=getBillMajor";
		//alert(url);
		var xmlrequest = getTransport();
		xmlrequest.open("GET", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
function LoadBill_Major(baseResponse)
{
	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	 if(flag=="success"){
		 var sel=document.getElementById("cmbBill_Major");
		 sel.length=0;
		 var option=document.createElement("OPTION");
		  option.value="";
		  option.text="--Select--";
		  sel.appendChild(option);
		  var len_Major=baseResponse.getElementsByTagName("Major_Len");
		  for(var i=0;i<len_Major.length;i++)
			  {
			  var MajorCode=baseResponse.getElementsByTagName("BILL_MAJOR_TYPE_CODE")[i].firstChild.nodeValue; 
			  var MajoeDesc=baseResponse.getElementsByTagName("BILL_MAJOR_TYPE_DESC")[i].firstChild.nodeValue; 
			  var sel=document.getElementById("cmbBill_Major");
			  var option=document.createElement("OPTION");
			  option.value=MajorCode;
			  option.text=MajoeDesc;
			  sel.appendChild(option);
			  }
	}

}
function billMinor()
{
var cmbBill_Major=document.getElementById("cmbBill_Major").value;
	var url =  "../../../../../Fas_GPFSanction_List_Repot?command=getBillMinor&cmbBill_Major="+cmbBill_Major;
	
	var xmlrequest = getTransport();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
	}

function LoadBill_Minor(baseResponse)
{
	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	 if(flag=="success"){
		 var sel=document.getElementById("cmbBill_Minor");
		 sel.length=0;
		
		 sel.value=""; 
		  var len_Minor=baseResponse.getElementsByTagName("Minor_Len");
		
		  for(var i=0;i<len_Minor.length;i++)
			  {
			  var MinorCode=baseResponse.getElementsByTagName("BILL_MINOR_TYPE_CODE")[i].firstChild.nodeValue; 
			  var MinorDesc=baseResponse.getElementsByTagName("BILL_MINOR_TYPE_DESC")[i].firstChild.nodeValue; 
			  var sel=document.getElementById("cmbBill_Minor");
			  var option=document.createElement("OPTION");
			  option.value=MinorCode;
			  option.text=MinorDesc;
			  sel.appendChild(option);
			  }
	} if(flag=="failure"){
		 var sel=document.getElementById("cmbBill_Minor");
		 sel.length=0;
		  var option=document.createElement("OPTION");
		  option.value="";
		  option.text="--Select--";
		  sel.appendChild(option);
	  }
}

function subType()
{
var cmbBill_Major=document.getElementById("cmbBill_Major").value;
var cmbBill_Minor=document.getElementById("cmbBill_Minor").value;
	var url =  "../../../../../Fas_GPFSanction_List_Repot?command=getSubType&cmbBill_Major="+cmbBill_Major+"&cmbBill_Minor="+cmbBill_Minor;
	
	var xmlrequest = getTransport();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
	}

function LoadBill_Minor(baseResponse)
{
	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	 if(flag=="success"){
		 var sel=document.getElementById("cmbBill_Minor");
		 sel.length=0;
		
		 sel.value=""; 
		  var len_Minor=baseResponse.getElementsByTagName("Minor_Len");
		  var option=document.createElement("OPTION");
		  option.value="";
		  option.text="--Select--";
		  sel.appendChild(option);
		
		  for(var i=0;i<len_Minor.length;i++)
			  {
			  var MinorCode=baseResponse.getElementsByTagName("BILL_MINOR_TYPE_CODE")[i].firstChild.nodeValue; 
			  var MinorDesc=baseResponse.getElementsByTagName("BILL_MINOR_TYPE_DESC")[i].firstChild.nodeValue; 
			  var sel=document.getElementById("cmbBill_Minor");
			  var option=document.createElement("OPTION");
			  option.value=MinorCode;
			  option.text=MinorDesc;
			  sel.appendChild(option);
			  }
	} if(flag=="failure"){
		 var sel=document.getElementById("cmbBill_Minor");
		 sel.length=0;
		  var option=document.createElement("OPTION");
		  option.value="";
		  option.text="--Select--";
		  sel.appendChild(option);
	  }
}
function LoadSub_Type(baseResponse)
{
	
	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	 if(flag=="success"){
		 var sel=document.getElementById("cmbSub_Type");
		 sel.length=0;
		 sel.value=""; 
		  var len_Sub=baseResponse.getElementsByTagName("Sub_Len");
		var option=document.createElement("OPTION");
		  option.value="";
		  option.text="--Select--";
		  sel.appendChild(option);
		  for(var i=0;i<len_Sub.length;i++)
			  {
			  var SubTypeCode=baseResponse.getElementsByTagName("BILL_SUB_TYPE_CODE")[i].firstChild.nodeValue; 
			  var SubTypeDesc=baseResponse.getElementsByTagName("BILL_SUB_TYPE_DESC")[i].firstChild.nodeValue; 
			  var sel=document.getElementById("cmbSub_Type");
			  var option=document.createElement("OPTION");
			  option.value=SubTypeCode;
			  option.text=SubTypeDesc;
			  sel.appendChild(option);
			  }
	} if(flag=="failure"){
		 var sel=document.getElementById("cmbSub_Type");
		 sel.length=0;
		  var option=document.createElement("OPTION");
		  option.value="";
		  option.text="--Select--";
		  sel.appendChild(option);
	  }
}

function ReportGPF()
{
	
	var cmbBill_Major=document.getElementById("cmbBill_Major").value;
	var radbtn_Off=document.getElementById("radio_Val").value;
	if(radbtn_Off=="Type"){
		
	if(cmbBill_Major==""){
		
		
		alert("Select Major Type");
		document.getElementById("cmbBill_Major").focus();
		}
	else{
		var cmbBill_Minor=document.getElementById("cmbBill_Minor").value;
	if(cmbBill_Minor=="")cmbBill_Minor=0;
	var cmbSub_Type=document.getElementById("cmbSub_Type").value;
	if(cmbSub_Type=="")cmbSub_Type=0;
	
	var txtoption=document.getElementById("txtoption").value;
	var url = "../../../../../Fas_GPFSanction_List_Repot?command="+radbtn_Off+"&cmbBill_Major="
			+ cmbBill_Major + "&cmbBill_Minor=" + cmbBill_Minor+"&cmbSub_Type="+cmbSub_Type+"&txtoption="+txtoption;
	// alert(url);
	document.frmGPFSanction.action=url;
      document.frmGPFSanction.submit();
	}
	}
	if(radbtn_Off=="All"){
		
		if(cmbBill_Major==""){
			cmbBill_Major=0;
		}
			
	var cmbBill_Minor=document.getElementById("cmbBill_Minor").value;
	if(cmbBill_Minor=="")cmbBill_Minor=0;
	var cmbSub_Type=document.getElementById("cmbSub_Type").value;
	if(cmbSub_Type=="")cmbSub_Type=0;
	
	var txtoption=document.getElementById("txtoption").value;
	var url = "../../../../../Fas_GPFSanction_List_Repot?command="+radbtn_Off+"&cmbBill_Major="
			+ cmbBill_Major + "&cmbBill_Minor=" + cmbBill_Minor+"&cmbSub_Type="+cmbSub_Type+"&txtoption="+txtoption;
	//alert(url);
	document.frmGPFSanction.action=url;
      document.frmGPFSanction.submit();
	}
		

}
function ChngeAll()
{
if(document.frmGPFSanction.All.checked == true){
	document.frmGPFSanction.Type.checked=false;
		document.frmGPFSanction.cmbBill_Major.disabled = true;
		document.frmGPFSanction.cmbBill_Minor.disabled=true;
		document.frmGPFSanction.cmbSub_Type.disabled=true;
		document.getElementById("radio_Val").value="All";
	
}}
function ChngeType()
{
 if(document.frmGPFSanction.Type.checked == true){
	document.frmGPFSanction.All.checked=false;
	document.frmGPFSanction.cmbBill_Major.disabled = false;
	document.frmGPFSanction.cmbBill_Minor.disabled=false;
	document.frmGPFSanction.cmbSub_Type.disabled=false;
	document.getElementById("radio_Val").value="Type";
	
}
}
