/////////////////////////////creating AJAX object////////////////////////

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
function initialLoad()
{
        var cheqtype=document.cheqpayeelistform.cheqtype.value;
        //alert(cheqtype);
		var url="../../../../../Cheqmemo_payee?command=loadlist&cheqtype="+cheqtype;
        //alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
                processResponse1(req);
        };   
        req.send(null);
}
function processResponse1(req)
{   
    if(req.readyState==4)   // Completed
      {
          if(req.status==200)   // No error
          {   
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
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
            var len=baseResponse.getElementsByTagName("serial_no").length;  
            
            for(var k=0;k<len;k++)
                {
                     var serial_no = baseResponse.getElementsByTagName("serial_no")[k].firstChild.nodeValue;
                     var cheqmemo_code = baseResponse.getElementsByTagName("cheqmemo_code")[k].firstChild.nodeValue;
                     var cheqmemo_desc = baseResponse.getElementsByTagName("cheqmemo_desc")[k].firstChild.nodeValue;
                     var payee_code = baseResponse.getElementsByTagName("payee_code")[k].firstChild.nodeValue;
                     var payee_desc = baseResponse.getElementsByTagName("payee_desc")[k].firstChild.nodeValue;
                     var view = baseResponse.getElementsByTagName("status")[k].firstChild.nodeValue;
                     
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=serial_no;
                     var cell=document.createElement("TD");
                     if (view == "C") {
               			//var tid = document.createTextNode("Cancel");			
               			var priceSpan = document.createElement("span");
               			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
               			priceSpan.appendChild(document.createTextNode("Cancel"));			
               			cell.appendChild(priceSpan);          			
               		}else{
               			var anc=document.createElement("A");       
                        var url="javascript:loadValuesFromTable('" +serial_no+ "')";   
                        anc.href=url;
                        var txtedit=document.createTextNode("Edit");
                        anc.appendChild(txtedit);
                        cell.appendChild(anc);          						
               		}                     
                     mycurrent_row.appendChild(cell);
                     
                     var cell1 =document.createElement("TD");   
                     cell1.setAttribute('align','center');
                     var tnodeserial_no=document.createTextNode(serial_no);                         
                     cell1.appendChild(tnodeserial_no);       
                     mycurrent_row.appendChild(cell1);
                     var hidden1=document.createElement("input");
                     hidden1.type="hidden";
                     hidden1.name="hidserial_no";
                     hidden1.value=serial_no;
                     cell1.appendChild(hidden1);
                     mycurrent_row.appendChild(cell1); 
                     
                     var cell2 =document.createElement("TD");    
                     cell2.setAttribute('align','right');
                     var tnodecheqmemo_desc=document.createTextNode(cheqmemo_desc);                         
                     cell2.appendChild(tnodecheqmemo_desc);       
                     mycurrent_row.appendChild(cell2);
                     var hidden2=document.createElement("input");
                     hidden2.type="hidden";
                     hidden2.name="hidcheqmemo_code";
                     hidden2.value=cheqmemo_code;
                     cell2.appendChild(hidden2);
                     mycurrent_row.appendChild(cell2); 
                     
                     var cell3 =document.createElement("TD"); 
                     var tnodepayee_desc=document.createTextNode(payee_desc);     
                     cell3.appendChild(tnodepayee_desc);       
                     mycurrent_row.appendChild(cell3);
                     var hidden3=document.createElement("input");
                     hidden3.type="hidden";
                     hidden3.name="hidpayee_code";
                     hidden3.value=payee_code;
                     cell3.appendChild(hidden3);
                     mycurrent_row.appendChild(cell3);
                     
                     var td5 = document.createElement("TD");
             		  if(view=="C"){
             			  var tdst = document.createTextNode("CANCEL");
             		  }else{
             			  var tdst = document.createTextNode("LIVE");
             		  }
             		  td5.appendChild(tdst);
             		  mycurrent_row.appendChild(td5);
                             
                     tbody.appendChild(mycurrent_row);
                    
                }
      }
      else
      {
        alert("Failed to Load Values");
      }           
    }
function loadValuesFromTable(serial_no)
    {         
          Minimize();
          opener.doParentEmp(serial_no);
    }

function Minimize()
{
   window.close();
   opener.window.focus();
}