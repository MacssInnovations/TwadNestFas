var seq=0;
var serialNo=1;
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
function loadhead()
{
 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
 var url = "../../../../../Bank_AccountHeadCode_Module.view?Command=loadhead&cmbAcc_UnitCode="+ cmbAcc_UnitCode;
            var req = getTransport();
            req.open("GET", url, true);
            req.onreadystatechange = function() {
                back_response(req);
            }
            req.send(null); 
}
function back_response(req)
{ 
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
          
            if(Command=="loadhead")
            {
            	loadheadChecking(baseResponse);
            }
           
            
        }
    }
}

function loadheadChecking(baseResponse)
{

var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
            var service=baseResponse.getElementsByTagName("leng_head");
           var tbody=document.getElementById("grid_body");
                         try{tbody.innerHTML="";}
                         catch(e) {tbody.innerText="";}
            for(var i=0;i<service.length;i++)
            {
            	
                                        var items=new Array();
		                        items[0]=service[i].getElementsByTagName("bankName")[0].firstChild.nodeValue;
                                items[1]=service[i].getElementsByTagName("branchName")[0].firstChild.nodeValue;
		                        items[2]=service[i].getElementsByTagName("bank_acc_no")[0].firstChild.nodeValue;
		                        items[3]=service[i].getElementsByTagName("account_head")[0].firstChild.nodeValue;
                                items[4]=service[i].getElementsByTagName("status")[0].firstChild.nodeValue;
		                       
                                       var tbody=document.getElementById("grid_body");
		                        var mycurrent_row=document.createElement("TR");                
                                        mycurrent_row.id=seq;
                                                
                                        var cell2=document.createElement("TD");       
                                        var slno=document.createElement("input");
                                        slno.type="hidden";
                                        slno.name="slno";
                                        slno.value=serialNo;
                                        cell2.appendChild(slno);
                                        var currentText=document.createTextNode(serialNo);
                                        cell2.appendChild(currentText);
                                        mycurrent_row.appendChild(cell2);
                                        
                                       var cell2=document.createElement("TD");       
                                        var typeid_one=document.createElement("input");
                                        typeid_one.type="hidden";
                                        typeid_one.name="type_id";
                                        typeid_one.value=items[0];
                                        cell2.appendChild(typeid_one);
                                        var currentText=document.createTextNode(items[0]);
                                        cell2.appendChild(currentText);
                                        mycurrent_row.appendChild(cell2);
                                       
                                        var cell3=document.createElement("TD");       
                                        var modeid_one=document.createElement("input");
                                        modeid_one.type="hidden";
                                        modeid_one.name="mode_id";
                                        modeid_one.value=items[1];
                                        cell3.appendChild(modeid_one);
                                        var currentText=document.createTextNode(items[1]);
                                        cell3.appendChild(currentText);
                                        mycurrent_row.appendChild(cell3);
                                        
                                        var cell4=document.createElement("TD");       
                                        var module_id_0ne=document.createElement("input");
                                        module_id_0ne.type="hidden";
                                        module_id_0ne.name="module_id";
                                        module_id_0ne.value=items[3];
                                        cell4.appendChild(module_id_0ne);
                                        var currentText=document.createTextNode(items[3]);//items[2]
                                        cell4.appendChild(currentText);
                                        mycurrent_row.appendChild(cell4);
                                       
                                        var cell5=document.createElement("TD");       
                                        var cr_type=document.createElement("input");
                                        cr_type.type="hidden";
                                        cr_type.name="cr_dr_type";
                                        cr_type.value=items[2];
                                        cell5.appendChild(cr_type);
                                        var currentText=document.createTextNode(items[2]);
                                        cell5.appendChild(currentText);
                                        mycurrent_row.appendChild(cell5);
                                        
                                        var cell6=document.createElement("TD");       
                                        var status_one=document.createElement("input");
                                        status_one.type="hidden";
                                        status_one.name="status_code";
                                        status_one.value=items[4];
                                        cell6.appendChild(status_one);
                                        var currentText=document.createTextNode(items[4]);
                                        cell6.appendChild(currentText);
                                        mycurrent_row.appendChild(cell6);
                                        
                                       
		                      
		                        tbody.appendChild(mycurrent_row);
                                        seq=seq+1;
                                        serialNo=serialNo+1;
            }
    
    }
    else if(flag=="noData")
    {
         alert("No Data Found");
    }
    else
    {
         alert("Error in Loading Grid");
    }
}