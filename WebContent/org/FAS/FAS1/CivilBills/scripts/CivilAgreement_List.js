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
        var unitid=document.civillistform.unit_id.value;
        var officeid=document.civillistform.office_id.value;
        var url="../../../../../CivilAgreement?command=gett&unitid1="+unitid+"&officeid1="+officeid;
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
            var len=baseResponse.getElementsByTagName("agreeno").length;              
            for(var k=0;k<len;k++)
                {
                     var agreeno = baseResponse.getElementsByTagName("agreeno")[k].firstChild.nodeValue;
                     var agreedate = baseResponse.getElementsByTagName("agreedate")[k].firstChild.nodeValue;
                     var agreetype = baseResponse.getElementsByTagName("agreetype")[k].firstChild.nodeValue;
                     var ledgercode = baseResponse.getElementsByTagName("ledgercode")[k].firstChild.nodeValue;
                     var firmname = baseResponse.getElementsByTagName("firmname")[k].firstChild.nodeValue;
                     var contractorname = baseResponse.getElementsByTagName("contractorname")[k].firstChild.nodeValue;
                     var firmContractor;
                     if(ledgercode==2)
                     {
                         firmContractor=firmname;
                     }
                     else
                     {
                         firmContractor=contractorname;
                     }
                     if(firmContractor=="null")
                     firmContractor="";
                     var valueofwork = baseResponse.getElementsByTagName("valueofwork")[k].firstChild.nodeValue;
                     var remarks = baseResponse.getElementsByTagName("remarks")[k].firstChild.nodeValue;
                  
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=agreeno;
                     var cell=document.createElement("TD");   
                     var anc=document.createElement("A");       
                     var url="javascript:loadValuesFromTable('" +agreeno + "')";     
                     
                     anc.href=url;
                     var txtedit=document.createTextNode("Edit");
                     anc.appendChild(txtedit);
                     cell.appendChild(anc);
                     mycurrent_row.appendChild(cell);
                  
                     var cell1 =document.createElement("TD");    
                     cell1.setAttribute('align','right');
                     var tnodeofficeid=document.createTextNode(agreeno);                         
                     cell1.appendChild(tnodeofficeid);       
                     mycurrent_row.appendChild(cell1);
                             
                     var cell2 =document.createElement("TD");   
                     cell2.setAttribute('align','left');
                     var tnodeagreedate=document.createTextNode(agreedate);                         
                     cell2.appendChild(tnodeagreedate);       
                     mycurrent_row.appendChild(cell2);
                     
                     var cell3 =document.createElement("TD");  
                     cell3.setAttribute('align','left');
                     var tnodeagreetype=document.createTextNode(agreetype);                         
                     cell3.appendChild(tnodeagreetype);       
                     mycurrent_row.appendChild(cell3);
                     
                     var cell4 =document.createElement("TD"); 
                     cell4.setAttribute('align','left');
                     var tnode1=document.createTextNode(firmContractor); 
                     cell4.appendChild(tnode1);       
                     mycurrent_row.appendChild(cell4);
                     
                     var cell5 =document.createElement("TD"); 
                     cell5.setAttribute('align','right');
                     var tnodevalue=document.createTextNode(valueofwork); 
                     cell5.appendChild(tnodevalue);       
                     mycurrent_row.appendChild(cell5);       
                    
                     var cell6 =document.createElement("TD");   
                     cell6.setAttribute('align','left');
                     var tnoderemarks=document.createTextNode(remarks);                         
                     cell6.appendChild(tnoderemarks);       
                     mycurrent_row.appendChild(cell6);
                     
                     tbody.appendChild(mycurrent_row);
                }
      }
      else
      {
        alert("No Records to Load");
      }           
    }
function loadValuesFromTable(agreeno)
    {       
         Minimize();
          opener.doParentEmp(agreeno);
    }

function Minimize()
{
   window.close();
   opener.window.focus();
}