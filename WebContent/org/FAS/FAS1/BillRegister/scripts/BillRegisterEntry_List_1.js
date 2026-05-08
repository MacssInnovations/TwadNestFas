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
function initialLoad()
{
        var unitid=document.billlistform.unit_id.value;
        var officeid=document.billlistform.office_id.value;
        var url="../../../../../BillRegisterEntry?command=gett&unitid1="+unitid+"&officeid1="+officeid;
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
                  if(command=="gett")
                      { 
                          getRow(baseResponse);
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
            var table = document.getElementById("Existing");
            var t=0;
            for(t=tbody.rows.length-1;t>=0;t--)
                {
                   tbody.deleteRow(0);
                }                        
            var len=baseResponse.getElementsByTagName("billRegisterno").length;              
            for(var k=0;k<len;k++)
                {
                     var billRegisterno = baseResponse.getElementsByTagName("billRegisterno")[k].firstChild.nodeValue;
                     var billRegisterdate = baseResponse.getElementsByTagName("billRegisterdate")[k].firstChild.nodeValue;
                     var proceedingno = baseResponse.getElementsByTagName("proceedingno")[k].firstChild.nodeValue;
                     var proceedingdate = baseResponse.getElementsByTagName("proceedingdate")[k].firstChild.nodeValue;
                     var ledgertype = baseResponse.getElementsByTagName("ledgertype")[k].firstChild.nodeValue;
                     var remarks = baseResponse.getElementsByTagName("remarks")[k].firstChild.nodeValue;
                     
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=billRegisterno;
                     var cell=document.createElement("TD");   
                     var anc=document.createElement("A");       
                     var url="javascript:loadValuesFromTable('" +billRegisterno + "')";     
                     
                     anc.href=url;
                     var txtedit=document.createTextNode("Edit");
                     anc.appendChild(txtedit);
                     cell.appendChild(anc);
                     mycurrent_row.appendChild(cell);
                  
                     var cell1 =document.createElement("TD");    
                     cell1.setAttribute('align','right');
                     var tnode1=document.createTextNode(billRegisterno);                         
                     cell1.appendChild(tnode1);       
                     mycurrent_row.appendChild(cell1);
                     
                     var cell2 =document.createElement("TD");   
                     cell2.setAttribute('align','left');
                     var tnode2=document.createTextNode(billRegisterdate);                         
                     cell2.appendChild(tnode2);       
                     mycurrent_row.appendChild(cell2);
                             
                     var cell3 =document.createElement("TD");   
                     cell3.setAttribute('align','left');
                     var tnode3=document.createTextNode(proceedingno);                         
                     cell3.appendChild(tnode3);       
                     mycurrent_row.appendChild(cell3);
                     
                     var cell4 =document.createElement("TD");  
                     cell4.setAttribute('align','left');
                     var tnode4=document.createTextNode(proceedingdate);                         
                     cell4.appendChild(tnode4);       
                     mycurrent_row.appendChild(cell4);
                     
                     var cell5 =document.createElement("TD"); 
                     cell5.setAttribute('align','left');
                     var tnode5=document.createTextNode(ledgertype); 
                     cell5.appendChild(tnode5);       
                     mycurrent_row.appendChild(cell5);
                     
                     var cell6 =document.createElement("TD");   
                     cell6.setAttribute('align','left');
                     var tnode6=document.createTextNode(remarks);                         
                     cell6.appendChild(tnode6);       
                     mycurrent_row.appendChild(cell6);
                     
                     tbody.appendChild(mycurrent_row);
                }
      }
      else
      {
        alert("Failed to Load Values");
      }           
    }
function loadValuesFromTable(billRegisterno)
{         
      Minimize();
      opener.doParentBill(billRegisterno);
}

function Minimize()
{
   window.close();
   opener.window.focus();
}
