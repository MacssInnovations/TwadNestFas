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
        var url="../../../../../PhoneCertificate?command=loadlist";
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
            var len=baseResponse.getElementsByTagName("phone_no").length;  
            //alert(len);
            for(var k=0;k<len;k++)
                {
                     var phone_certi_no = baseResponse.getElementsByTagName("phone_certificate_no")[k].firstChild.nodeValue;
                     
                     var phone_no = baseResponse.getElementsByTagName("phone_no")[k].firstChild.nodeValue;
                     var bill_month = baseResponse.getElementsByTagName("bill_month")[k].firstChild.nodeValue;
                     var bill_year = baseResponse.getElementsByTagName("bill_year")[k].firstChild.nodeValue;
                     var invoice_no = baseResponse.getElementsByTagName("invoice_no")[k].firstChild.nodeValue;
                     var certi_text = baseResponse.getElementsByTagName("certi_text")[k].firstChild.nodeValue;
                     var view = baseResponse.getElementsByTagName("status")[k].firstChild.nodeValue;
                     
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=phone_certi_no;
                     var cell=document.createElement("TD");
                     if (view == "C") {
               			//var tid = document.createTextNode("Cancel");			
               			var priceSpan = document.createElement("span");
               			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
               			priceSpan.appendChild(document.createTextNode("Cancel"));			
               			cell.appendChild(priceSpan);          			
               		}else{
               			var anc=document.createElement("A");       
                        var url="javascript:loadValuesFromTable('" +phone_certi_no+ "')";     
                        anc.href=url;
                        var txtedit=document.createTextNode("Edit");
                        anc.appendChild(txtedit);
                        cell.appendChild(anc);          						
               		}                     
                     mycurrent_row.appendChild(cell);
                  
                     var cell1 =document.createElement("TD");    
                     cell1.setAttribute('align','right');
                     var tnodephone_certi_no=document.createTextNode(phone_certi_no);                         
                     cell1.appendChild(tnodephone_certi_no);       
                     mycurrent_row.appendChild(cell1);
                             
                     var cell2 =document.createElement("TD");   
                     cell2.setAttribute('align','left');
                     var tnodephone_no=document.createTextNode(phone_no);                         
                     cell2.appendChild(tnodephone_no);       
                     mycurrent_row.appendChild(cell2);
                     
                     var cell2 =document.createElement("TD");   
                     cell2.setAttribute('align','left');
                     var tnodebill_month=document.createTextNode(bill_month);                         
                     cell2.appendChild(tnodebill_month);       
                     mycurrent_row.appendChild(cell2);
                     
                     var cell2 =document.createElement("TD");   
                     cell2.setAttribute('align','left');
                     var tnodebill_year=document.createTextNode(bill_year);                         
                     cell2.appendChild(tnodebill_year);       
                     mycurrent_row.appendChild(cell2);
                     
                     var cell2 =document.createElement("TD");   
                     cell2.setAttribute('align','left');
                     var tnodeinvoice_no=document.createTextNode(invoice_no);                         
                     cell2.appendChild(tnodeinvoice_no);       
                     mycurrent_row.appendChild(cell2);
                     
                     var cell2 =document.createElement("TD");   
                     cell2.setAttribute('align','left');
                     var tnodecerti_text=document.createTextNode(certi_text);                         
                     cell2.appendChild(tnodecerti_text);       
                     mycurrent_row.appendChild(cell2);
                     
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
function loadValuesFromTable(phone_certi_no)
    {         
          Minimize();
          opener.doParentEmp1(phone_certi_no);
    }

function Minimize()
{
   window.close();
   opener.window.focus();
}