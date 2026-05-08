var service;
var __pagination=11;
var destid;
var totalblock=0;

var Ucode;
var Offid;
var txtCB_Year;
var txtCB_Month;
var txtCB_Year1;
var txtCB_Month1;
var Receipt_list_SL;

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
    /*if((document.frmGeneralLedgerSystem.cmbAccHeadCode.value=="") || (document.frmGeneralLedgerSystem.cmbAccHeadCode.value.length<=0) || (document.frmGeneralLedgerSystem.cmbAccHeadCode.value=="0"))
    {
        alert("Please Select Account Head Code");
        document.frmGeneralLedgerSystem.cmbAccHeadCode.focus();
        return false;
    }*/
    
 
}



/**
 *  Numbers only Checking 
 */

function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
   
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48||unicode>57 ) 
            return false 
    }
 }
 
 
 
 /**
  *   Confirmation before Submiting Form 
  */
    
 
 function confirmation()
 {
 
  /** Call Cehcknull Function */
  checknull();
    var txtCB_Year=document.getElementById("txtCB_Year").value;
            var txtCB_Month=document.getElementById("txtCB_Month").value;
          //  alert("enter into function");
            if(txtCB_Year.length!=0 && txtCB_Month.length!=0)
            {
	                var url="../../../../../FreezeNillTBUnits?Command=grid&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
                       // alert(url);
	                var req=getTransport();
	                req.open("GET",url,true); 
	                req.onreadystatechange=function()
	                {
	                   handleResponse1(req);
	                }   
	                   req.send(null);
               
            }
  
   
  
  
  
 }
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

function handleResponse1(req)
{ 
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
        //alert(req);
           //  alert("Enter into response");
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             // alert(baseResponse);
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            // alert(Command);
            if(Command=="grid")
            {
            	
                loadTable(baseResponse);
            }
           
        }
    }
}

function loadTable(baseResponse)
{
alert("enter");
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
 
	if (flag == "success") {
		

		

		
		var len= baseResponse.getElementsByTagName("accname").length;
	
		
        for(k=0;k<len;k++)
        	{
        	//var empid = baseResponse.getElementsByTagName("empID")[k].lastChild.nodeValue;
        	var empid = baseResponse.getElementsByTagName("accname")[k].firstChild.nodeValue;
 
    		var Designation = baseResponse.getElementsByTagName("officename")[k].firstChild.nodeValue;


		var tbody = document.getElementById("tblList");
     
		var table = document.getElementById("Existing");

		var mycurrent_row = document.createElement("TR");
                var seq;
		mycurrent_row.id = seq;
		

		var cell = document.createElement("TD");
		var tnoderegno = document.createTextNode(Designation);
		cell.appendChild(tnoderegno);
		mycurrent_row.appendChild(cell);
alert("1");
		var cell2 = document.createElement("TD");
		var tnodeentrydate = document.createTextNode(empid);
		cell2.appendChild(tnodeentrydate);
		mycurrent_row.appendChild(cell2);
                alert("2");
		
		tbody.appendChild(mycurrent_row);
		seq++;
	}
	}else {
		alert("Failed to Add values");
	}
	
	

}
