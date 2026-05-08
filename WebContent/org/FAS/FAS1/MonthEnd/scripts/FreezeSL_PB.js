var seq=0;
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

function checknull()
{
    if(document.getElementById("txtCB_Month").value=="")
    {
        alert("Select a month");
        return false;
    }
    var tbody=document.getElementById("tbody");
    if(tbody.rows.length==0){
	alert("No Data To Submit");
	return false;
	}
   
 return true;
}
function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
   
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48||unicode>57 ) 
            return false 
    }
 }
 
 function CheckStatus()
 {

  var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
  var txtCB_Year=document.getElementById("txtCB_Year").value;
  var splyear=txtCB_Year.split("-");
  
  var txtCB_Month=document.getElementById("txtCB_Month").value;
  var url="../../../../../FreezeSL_PB_servlet?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtCB_Year1="+splyear[0]+"&txtCB_Year2="+splyear[1];
//        alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse(req);
        }   
    req.send(null);
 }
 function handleResponse(req)
{ 
	    if(req.readyState==4)
	    {
		        if(req.status==200)
		        {  
			            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
			            var tagcommand=baseResponse.getElementsByTagName("command")[0];
			            var Command=tagcommand.firstChild.nodeValue;
			             
			            if(Command=="searchByMonth")
			            {
			                checkStatus_one(baseResponse);
			            }
			           
		        }
	    }
}

/* function callmn()
{
	var today= new Date(); 
    var day=today.getDate();
    var month=today.getMonth();
    month=month+1;
    var mn=document.getElementById("txtCB_Month").value;
    if(mn!=month)
    {
    	document.getElementById("txtCB_Month").value=month;
    }
    
}  */

function checkStatus_one(baseResponse)
{
    
        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="NoData")
        {
        //alert("SL PB Already Frozen");
        	alert("No Data Found");
        }
        else
        {
                var tbody=document.getElementById("tbody");
                try{tbody.innerHTML="";}
                catch(e) {tbody.innerText="";} 
                
                  service=baseResponse.getElementsByTagName("leng");
              //  alert(service.length);
	              for(var i=0; i<service.length;i++)
	               {
                     
		                        var items=new Array();
		                        items[0]=service[i].getElementsByTagName("year")[0].firstChild.nodeValue;
                                        items[1]=service[i].getElementsByTagName("month")[0].firstChild.nodeValue;
		                        items[2]=service[i].getElementsByTagName("status")[0].firstChild.nodeValue;
		                        items[3]=service[i].getElementsByTagName("date_one")[0].firstChild.nodeValue;
                                      //  alert("lll");
		                        var tbody=document.getElementById("tbody");
		                        var mycurrent_row=document.createElement("TR");                
                                        mycurrent_row.id=seq;
                                                
                                        var cell2=document.createElement("TD");       
                                        var cyear=document.createElement("input");
                                        cyear.type="hidden";
                                        cyear.name="cash_year";
                                        cyear.value=items[0];
                                        cell2.appendChild(cyear);
                                        var currentText=document.createTextNode(items[0]);
                                        cell2.appendChild(currentText);
                                        mycurrent_row.appendChild(cell2);
                                       
                                        var cell3=document.createElement("TD");       
                                        var cname=document.createElement("input");
                                        cname.type="hidden";
                                        cname.name="cash_month";
                                        cname.value=items[1];
                                        cell3.appendChild(cname);
                                        var currentText=document.createTextNode(items[1]);
                                        cell3.appendChild(currentText);
                                        mycurrent_row.appendChild(cell3);
                                        
                                        var cell4=document.createElement("TD");       
                                        var s_status=document.createElement("input");
                                        s_status.type="hidden";
                                        s_status.name="status";
                                        s_status.value=items[2];
                                        cell4.appendChild(s_status);
                                        var currentText=document.createTextNode(items[2]);
                                        cell4.appendChild(currentText);
                                        mycurrent_row.appendChild(cell4);
                                        
                                        var cell8=document.createElement("TD");       
                                        var s_date=document.createElement("input");
                                        s_date.type="hidden";
                                        s_date.name="status_date";
                                        s_date.value=items[3];
                                        cell8.appendChild(s_date);
                                        var currentText=document.createTextNode(items[3]);
                                        cell8.appendChild(currentText);
                                        mycurrent_row.appendChild(cell8);
                                       
		                        tbody.appendChild(mycurrent_row);
                                        seq=seq+1;
		        }
        }
}