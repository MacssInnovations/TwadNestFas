var seq=0;
function getxmlhttpObject()
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

function deleteGrid()
{
	var proformatype=document.getElementById("proformatype").value;
	if(proformatype=="TPAAC1" || proformatype=="TPAAD1"){
		var tbod=document.getElementById("grid_body");
	
		for(t=tbod.rows.length-1;t>=0;t--)
		{
		        tbod.deleteRow(0);
		       
		}
		var secDiv=document.getElementById("secDiv");
		secDiv.style.display="none";
		var firstDiv=document.getElementById("firstDiv");
		firstDiv.style.display="block";
	}
	else
	{
		var tbod=document.getElementById("grid_body_sec");
		
		for(t=tbod.rows.length-1;t>=0;t--)
		{
		        tbod.deleteRow(0);
		       
		}
		document.getElementById("firstDiv").style.display="none";
		var secDiv=document.getElementById("secDiv");
		secDiv.style.display="block";
	}
}

function call(command)
{
	
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }  



 if(command=="get")
{ 
	if(checknull())
	{
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var proformatype=document.getElementById("proformatype").value;
	var status=document.getElementById("cmbStatus").value;
		var cashmonth=document.getElementById("txtCB_Month").value;
		var cashyear=document.getElementById("txtCB_Year").value;
	  var url="../../../../../TPA_Acceptance_List?command=get&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&searchby=yearmonth&cmbOffice_code="+cmbOffice_code+"&proformatype="+proformatype+"&status="+status+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"";
	 
	  url=url+"&sid="+Math.random();
      xmlhttp.open("GET",url,true);
      xmlhttp.onreadystatechange=stateChanged;
      xmlhttp.send(null); 
	}
    
	
} 
 else if(command=="getbydate")
 { 
	 if(checknull())
		{
 	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
 		 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
 	var proformatype=document.getElementById("proformatype").value;
 	var status=document.getElementById("cmbStatus").value;
 	var cashmonth=document.getElementById("txtCB_Month").value;
	var cashyear=document.getElementById("txtCB_Year").value;
	
	if(document.getElementById("txtFrom_date").value=="")
	{
		alert('Please Enter From Date');
		return false;
	}
	if(document.getElementById("txtTo_date").value=="")
	{
		alert('Please Enter To Date');
		return false;
	}
 		var fromdate=document.getElementById("txtFrom_date").value;
 		var todate=document.getElementById("txtTo_date").value;
 	  var url="../../../../../TPA_Acceptance_List?command=get&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&searchby=date&cmbOffice_code="+cmbOffice_code+"&proformatype="+proformatype+"&status="+status+"&fromdate="+fromdate+"&todate="+todate+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"";
 	 
 	  url=url+"&sid="+Math.random();
       xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
       xmlhttp.send(null);  
		}
  
 } 
 
}


function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
//alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
{
call_clr();
var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
var cmbOffice_code=document.getElementById("cmbOffice_code").value;
var TB_date=fromcal_dateCtrl.value;
//alert(fromcal_dateCtrl.value+"b4url")
if(fromcal_dateCtrl.value.length!=0)
{
var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
//alert(url);
var req=getTransport();
req.open("GET",url,true); 
req.onreadystatechange=function()
{
check_TB(req,fromcal_dateCtrl);
}   
req.send(null);
}
}
}





function stateChanged()
{
    var flag,command,response;
   
    if(xmlhttp.readyState==4)
    {
    	
       if(xmlhttp.status==200)
       {
            response=xmlhttp.responseXML.getElementsByTagName("response")[0];
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(command=="get")
            {
        
                if(flag=='success')
                {
                	var proformatype=document.getElementById("proformatype").value;
                	if(proformatype=="TPAAC1" || proformatype=="TPAAD1"){
                	
                	
                		try{
                		
		              		  var len=response.getElementsByTagName("vno").length;
			                 
			                var	tbody = document.getElementById("grid_body");
			                
			                var t=0;
			                for(t=tbody.rows.length-1;t>=0;t--)
			                {
			                        tbody.deleteRow(0);
			                       
			                }
		                	var unitid=response.getElementsByTagName("unitid")[0].firstChild.nodeValue;	
		                	var officeid=response.getElementsByTagName("officeid")[0].firstChild.nodeValue;	
		                	var cashyear=response.getElementsByTagName("cashyear")[0].firstChild.nodeValue;	
		                	var cashmonth=response.getElementsByTagName("cashmonth")[0].firstChild.nodeValue;	
		                	
		                	
		                	for(var i=0;i<len;i++)
		                	{
		                		
			                var vno=response.getElementsByTagName("vno")[i].firstChild.nodeValue;	
			                var vdate=response.getElementsByTagName("vdate")[i].firstChild.nodeValue;		
			                var oUnitname=response.getElementsByTagName("oUnitname")[i].firstChild.nodeValue;		
			                var particular=response.getElementsByTagName("particular")[i].firstChild.nodeValue;		
			                var reason=response.getElementsByTagName("reason")[i].firstChild.nodeValue;
			                var amount=response.getElementsByTagName("amount")[i].firstChild.nodeValue;		              		
		                		
		                	var mycurrent_row = document.createElement("TR");
		                	seq = seq;
		                	mycurrent_row.id = seq;
		                	
		                	
		                	
		                	var cell2;
		                	
		                	cell2 = document.createElement("TD");
		                	var currentText = document.createTextNode(oUnitname);
		                	cell2.appendChild(currentText);
		                	mycurrent_row.appendChild(cell2);
		                	
		                	cell2 = document.createElement("TD");
		                	var currentText = document.createTextNode(vno);
		                	cell2.appendChild(currentText);
		                	mycurrent_row.appendChild(cell2);
		
		                	cell2 = document.createElement("TD");
		                	var currentText = document.createTextNode(vdate);
		                	cell2.appendChild(currentText);
		                	mycurrent_row.appendChild(cell2);
		
		                /*	cell2 = document.createElement("TD");
		                	var currentText = document.createTextNode(reason);
		                	cell2.appendChild(currentText);
		                	mycurrent_row.appendChild(cell2);*/
		
		                	cell2 = document.createElement("TD");
		
		                	var currentText = document.createTextNode(particular);
		                	cell2.appendChild(currentText);
		                	mycurrent_row.appendChild(cell2);
		                	
		                	cell2 = document.createElement("TD");
		                	var currentText = document.createTextNode(reason);
		                	cell2.appendChild(currentText);
		                	mycurrent_row.appendChild(cell2);
		                	
		                	cell2 = document.createElement("TD");
		                	cell2.align="right";
		                	var currentText = document.createTextNode(amount);
		                	cell2.appendChild(currentText);
		                	mycurrent_row.appendChild(cell2);
		
		                	var cell = document.createElement("TD");
		                	var anc = document.createElement("A");
		                	var url = "javascript:loadTable('" + unitid+ "','"+officeid+"','"+cashyear+"','"+cashmonth+"','"+vno+"')";
		                	anc.href = url;
		                	var txtedit = document.createTextNode("Details");
		                	anc.appendChild(txtedit);
		                	cell.appendChild(anc);
		                	mycurrent_row.appendChild(cell);
		
		                	
		                	tbody.appendChild(mycurrent_row);
		                	
		                	}
		                	}catch(e){}
                	}
                	else {
                	
                	
                		try{
                		
		              		  var len=response.getElementsByTagName("vno").length;
			                 
			                var	tbody = document.getElementById("grid_body_sec");
			                
			                var t=0;
			                for(t=tbody.rows.length-1;t>=0;t--)
			                {
			                        tbody.deleteRow(0);
			                       
			                }
		                	var unitid=response.getElementsByTagName("unitid")[0].firstChild.nodeValue;	
		                	var officeid=response.getElementsByTagName("officeid")[0].firstChild.nodeValue;	
		                	var cashyear=response.getElementsByTagName("cashyear")[0].firstChild.nodeValue;	
		                	var cashmonth=response.getElementsByTagName("cashmonth")[0].firstChild.nodeValue;	
		                	
		                	
		                	for(var i=0;i<len;i++)
		                	{
		                		
			                var vno=response.getElementsByTagName("vno")[i].firstChild.nodeValue;	
			                var vdate=response.getElementsByTagName("vdate")[i].firstChild.nodeValue;		
			                var oUnitname=response.getElementsByTagName("oUnitname")[i].firstChild.nodeValue;

			                var accSlNo=response.getElementsByTagName("accSlNo")[i].firstChild.nodeValue;
			                var accDate=response.getElementsByTagName("accDate")[i].firstChild.nodeValue;
			                
			                var particular=response.getElementsByTagName("particular")[i].firstChild.nodeValue;		
			                var reason=response.getElementsByTagName("reason")[i].firstChild.nodeValue;
			                var amount=response.getElementsByTagName("amount")[i].firstChild.nodeValue;		              		
		                		
		                	var mycurrent_row = document.createElement("TR");
		                	seq = seq;
		                	mycurrent_row.id = seq;
		                	
		                	
		                	
		                	var cell2;
		                	
		                	cell2 = document.createElement("TD");
		                	var currentText = document.createTextNode(oUnitname);
		                	cell2.appendChild(currentText);
		                	mycurrent_row.appendChild(cell2);
		                	
		                	cell2 = document.createElement("TD");
		                	var currentText = document.createTextNode(vno);
		                	cell2.appendChild(currentText);
		                	mycurrent_row.appendChild(cell2);
		
		                	cell2 = document.createElement("TD");
		                	var currentText = document.createTextNode(vdate);
		                	cell2.appendChild(currentText);
		                	mycurrent_row.appendChild(cell2);
		
		                   cell2 = document.createElement("TD");
		                	var currentText = document.createTextNode(accSlNo);
		                	cell2.appendChild(currentText);
		                	mycurrent_row.appendChild(cell2);
		                	
		                	cell2 = document.createElement("TD");
		                	var currentText = document.createTextNode(accDate);
		                	cell2.appendChild(currentText);
		                	mycurrent_row.appendChild(cell2);
		                	
		                	
		
		                	cell2 = document.createElement("TD");
		
		                	var currentText = document.createTextNode(particular);
		                	cell2.appendChild(currentText);
		                	mycurrent_row.appendChild(cell2);
		                	
		                	cell2 = document.createElement("TD");
		                	var currentText = document.createTextNode(reason);
		                	cell2.appendChild(currentText);
		                	mycurrent_row.appendChild(cell2);
		                	
		                	cell2 = document.createElement("TD");
		                	cell2.align="right";
		                	var currentText = document.createTextNode(amount);
		                	cell2.appendChild(currentText);
		                	mycurrent_row.appendChild(cell2);
		
		                	var cell = document.createElement("TD");
		                	var anc = document.createElement("A");
		                	var url = "javascript:loadTable('" + unitid+ "','"+officeid+"','"+cashyear+"','"+cashmonth+"','"+accSlNo+"')";
		                	anc.href = url;
		                	//alert(url);
		                	var txtedit = document.createTextNode("Details");
		                	anc.appendChild(txtedit);
		                	cell.appendChild(anc);
		                	mycurrent_row.appendChild(cell);
		
		                	
		                	tbody.appendChild(mycurrent_row);
		                	
		                	}
		                	}catch(e){}
                	}
                }
                else{
                	if(proformatype=="TPAAC1" || proformatype=="TPAAD1"){
                		var	tbody = document.getElementById("grid_body");
                        
                        var t=0;
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                                tbody.deleteRow(0);
                               
                        }
                	}
                	else
                	{
                		var	tbody = document.getElementById("grid_body_sec");
                        
                        var t=0;
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                                tbody.deleteRow(0);
                               
                        }	
                	}
                	 
                     alert('No Data Found');
                }
            }
       }
    }
}

var tpalist;

function loadTable(unit,office,cashyear,cashmonth,vno)
{
	 if (tpalist && tpalist.open && !tpalist.closed) 
     {
		 tpalist.resizeTo(500,500);
		 tpalist.moveTo(250,250); 
		 tpalist.focus();
     }
     else
     {
    	 tpalist=null;
     }
    
    	   
	 tpalist= window.open("TPA_Acceptance_Transaction_List.jsp?cmbAcc_UnitCode="+unit+"&cmbOffice_code="+office+"&cashyear="+cashyear+"&cashmonth="+cashmonth+"&vno="+vno,"TPAList","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
    
	 tpalist.moveTo(250,250);  
	 tpalist.focus();	
}

function checknull()
{

	if(document.getElementById("proformatype").value=="")
	{
		alert('Please Select Transfer Proforma Type');
		return flase;
	}
	if(document.getElementById("txtCB_Year").value=="")
	{
		alert('Please Enter CashBook Year');
		return false;
	}
 return true;	

}


function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
        if(unicode==13)
        {
          //t.blur();
          //return true;-------------------- for taking action when press ENTER
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48 || unicode>57 ) 
                return false; 
        }
     }
