function AjaxFunction()
    {
        var xmlrequest=false;
        try
            {
               xmlrequest=new ActiveXObject("Msxml2.XMLHTTP"); //mozilla
            }
         catch(e1)
          {
                 try
                 {
                     xmlrequest=new ActiveXObject("Microsoft.XMLHTTP"); //IE
                 }
                 catch(e2)
                 {     
                     xmlrequest=false;
                 }
          }
          if (!xmlrequest && typeof XMLHttpRequest != 'undefined') 
                {
                     xmlrequest=new XMLHttpRequest();
                }
        return xmlrequest;
    } 

function doFunction1()
    {
        var mon=document.listform.month.value;
        var yr=document.listform.year.value;
        
        
        var unitid=document.listform.unit_id.value;
        var officeid=document.listform.office_id.value;
      
        
        var cell=document.getElementById("allid");
        cell.style.display="none";
        var cell=document.getElementById("comboid");
        cell.style.display="none";
        var tbody = document.getElementById("tblList");        
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(t);
        }             
        var url="../../../../../FirmBills_Serv?command=gocmd&mon="+mon+"&yr="+yr+"&unitid1="+unitid+"&officeid1="+officeid;
        var xmlrequest= AjaxFunction();
        xmlrequest.open("GET",url,true); 
        xmlrequest.onreadystatechange=function()
            {
              manipulate(xmlrequest);
            }   
        xmlrequest.send(null);
    }
    
function calldefault()
    {
      gobtnFunction();  
    }

function gobtnFunction()
    {
     
        var cell=document.getElementById("allid");
        cell.style.display="block";
        var cell=document.getElementById("comboid");
        cell.style.display="none";
        document.listform.all[0].checked=true;
        var fromdate=document.listform.fromdate.value;
        var todate=document.listform.todate.value;
        var unitid=document.listform.unit_id.value;
        var officeid=document.listform.office_id.value;
        
        if(fromdate=="")
            {
                alert("enter from Date");
                document.listform.fromdate.focus();
            }
        else if(todate=="")
            {
                alert("enter to Date");
                document.listform.todate.focus();
            }
         else
        {
            var tbody = document.getElementById("tblList");
            for(t=tbody.rows.length-1;t>=0;t--)
            {
               tbody.deleteRow(t);
            }                 
            var url="../../../../../FirmBills_Serv?command=displaycmd&fromdate="+fromdate+"&todate="+todate+"&unitid1="+unitid+"&officeid1="+officeid;
            var xmlrequest= AjaxFunction();
            xmlrequest.open("GET",url,true); 
            xmlrequest.onreadystatechange=function()
            {
                manipulate(xmlrequest);
            }   
            xmlrequest.send(null);
        }
    }
    
function callfunc()
    {
        var cell=document.getElementById("comboid");
        cell.style.display="block";
        var fromdate=document.listform.fromdate.value; //particularcmd
        var todate=document.listform.todate.value;
        var unitid=document.listform.unit_id.value;
        var officeid=document.listform.office_id.value;
        var tbody = document.getElementById("tblList");
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(t);
        }      
        
        var url="../../../../../FirmBills_Serv?command=particularcommand&fromdate="+fromdate+"&todate="+todate+"&unitid1="+unitid+"&officeid1="+officeid;
        var xmlrequest= AjaxFunction();
        xmlrequest.open("GET",url,true); 
        xmlrequest.onreadystatechange=function()
            {
              manipulate(xmlrequest);
            }   
        xmlrequest.send(null);
    }
    
function callino()
    {
        var particularcmb=document.listform.particularcmb.value;         
        var tbody = document.getElementById("tblList");
        var unitid=document.listform.unit_id.value;
        var officeid=document.listform.office_id.value;
        
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(t);
        }            
        var url="../../../../../FirmBills_Serv?command=callino&particularcmb="+particularcmb+"&unitid1="+unitid+"&officeid1="+officeid;;;
        var xmlrequest= AjaxFunction();
        xmlrequest.open("GET",url,true); 
        xmlrequest.onreadystatechange=function()
            {
              manipulate(xmlrequest);
            }   
        xmlrequest.send(null);
    }

function  manipulate(xmlrequest)
    {
    if(xmlrequest.readyState==4)
      {
          if(xmlrequest.status==200)
          {
               var baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];  
               var tagCommand=baseResponse.getElementsByTagName("command")[0]; 
               var command=tagCommand.firstChild.nodeValue; 
               if(command=="gobutton1"||command=="gobutton2")
                   {
                       var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                       if(flag=="success")
                       {                            
                            getRow(baseResponse);
                       }
                       else
                       	alert("No Data Found");
                    }
               else if(command=="particularcommand") 
                    {
                        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                        if(flag=="success")
                            {
                                var tbody = document.getElementById("tblList");
                                var table = document.getElementById("Existing");
                                var t=0;
                                for(t=tbody.rows.length-1;t>=0;t--)
                                {
                                   tbody.deleteRow(t);
                                }          
                                var particularcmb = document.forms[0].particularcmb;    
                                var billno = baseResponse.getElementsByTagName("billno");  
                                for(var i=0; i<billno.length; i++)
                                     {
                                         var opt = document.createElement('option');
                                         opt.value = billno[i].firstChild.nodeValue;
                                         opt.innerHTML = billno[i].firstChild.nodeValue; 
                                         particularcmb.appendChild(opt);
                                     }
                            }
                    }
                 else if(command=="callino") 
                    {
                        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                        if(flag=="success")
                            {                               
                                getRow(baseResponse);
                            }
                        else
                        	alert("No Data Found");
                    }
          }
        }
    }

function  getRow(baseResponse)
    {    
        
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
      if(flag=="success")
        {         
             var tbody = document.getElementById("tblList");
             var len=baseResponse.getElementsByTagName("billno").length;              
             for(var k=0;k<len;k++)
                {
               	
                	 var aunitid = baseResponse.getElementsByTagName("ACCOUNTING_FOR_OFFICE_ID")[k].firstChild.nodeValue;
                  var aoffid = baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[k].firstChild.nodeValue;
	            	 var billno = baseResponse.getElementsByTagName("billno")[k].firstChild.nodeValue;
	                 var billdate = baseResponse.getElementsByTagName("billdate")[k].firstChild.nodeValue;
                     var ino = baseResponse.getElementsByTagName("invoiceno")[k].firstChild.nodeValue;
                     var ida = baseResponse.getElementsByTagName("invoicedate")[k].firstChild.nodeValue;
                     var invoiceAmt = baseResponse.getElementsByTagName("invoiceAmt")[k].firstChild.nodeValue;
                     var major = baseResponse.getElementsByTagName("marterdesc")[k].firstChild.nodeValue;
                     var minor = baseResponse.getElementsByTagName("minor")[k].firstChild.nodeValue;
                     var sub = baseResponse.getElementsByTagName("sub")[k].firstChild.nodeValue;
                     var invoiceparti = baseResponse.getElementsByTagName("invoiceparti")[k].firstChild.nodeValue;
                     var agreeno = baseResponse.getElementsByTagName("agreeno")[k].firstChild.nodeValue;
                     var agreedate = baseResponse.getElementsByTagName("agreedate")[k].firstChild.nodeValue;
                     var remarks = baseResponse.getElementsByTagName("remarks")[k].firstChild.nodeValue;
                     var firmname = baseResponse.getElementsByTagName("firmname")[k].firstChild.nodeValue;
                     var cell2; 
                     
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=ino;
                     var cell=document.createElement("TD");   
                     var anc=document.createElement("A");       
                     
                     var url="javascript:loadValuesFromTable("+billno+",'"+billdate+"','"+major+"','"+minor+"','"+sub+"','"+aunitid+"','"+aoffid+"')";   
                     anc.href=url;
                     var txtedit=document.createTextNode("Edit");
                     anc.appendChild(txtedit);
                     cell.appendChild(anc);
                     mycurrent_row.appendChild(cell);
                     
                     cell2 =document.createElement("TD"); 
                     var tnodebillno=document.createTextNode(billno);     
                     cell2.appendChild(tnodebillno);       
                     mycurrent_row.appendChild(cell2); 
                     
                     cell2 =document.createElement("TD"); 
                     var tnodebilldate=document.createTextNode(billdate);     
                     cell2.appendChild(tnodebilldate);       
                     mycurrent_row.appendChild(cell2);
                     
                     cell2 =document.createElement("TD"); 
                     var tnodeino=document.createTextNode(ino+"-"+ida);     
                     cell2.appendChild(tnodeino);       
                     mycurrent_row.appendChild(cell2);  
                     
                     cell2 =document.createElement("TD");    
                     var invoiceAmt=document.createTextNode(invoiceAmt);                         
                     cell2.appendChild(invoiceAmt);       
                     mycurrent_row.appendChild(cell2);
                    
                     cell2 =document.createElement("TD");    
                     var tnodemajor=document.createTextNode(major+"-"+minor+"-"+sub);                         
                     cell2.appendChild(tnodemajor);       
                     mycurrent_row.appendChild(cell2);
                             
                     cell2 =document.createElement("TD");    
                     var tinvoiceparti=document.createTextNode(invoiceparti);                         
                     cell2.appendChild(tinvoiceparti);       
                     mycurrent_row.appendChild(cell2);
                     
                     cell2 =document.createElement("TD");    
                     var tagreeno=document.createTextNode(agreeno+"-"+agreedate);                         
                     cell2.appendChild(tagreeno);       
                     mycurrent_row.appendChild(cell2);
                     
                     cell2 =document.createElement("TD");    
                     var firmname=document.createTextNode(firmname);                         
                     cell2.appendChild(firmname);       
                     mycurrent_row.appendChild(cell2);
            
                     cell2 =document.createElement("TD");    
                     var tremarks=document.createTextNode(remarks);                         
                     cell2.appendChild(tremarks);       
                     mycurrent_row.appendChild(cell2);
                     
                     tbody.appendChild(mycurrent_row);
                }
      }
      else
      {
        alert("Failed to Load Values");
      }           
    }

function loadValuesFromTable(billno,billdate,major,minor,sub,unitid,offid)
    {      
	//alert(billno+"  "+billdate+"   "+major+"  "+minor+"  "+sub);
          Minimize();
          opener.doParentEmp(billno,billdate,major,minor,sub,unitid,offid);
    }

function Minimize()
{
   window.close();
   opener.window.focus();
}