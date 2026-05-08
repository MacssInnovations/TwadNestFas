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
        var url="../../../../../Payeetype?command=loadlist";
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
                processResponse1(req);
        }   
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
            var len=baseResponse.getElementsByTagName("payee_code").length;  
            //alert(len);
            for(var k=0;k<len;k++)
                {
                     var payee_code = baseResponse.getElementsByTagName("payee_code")[k].firstChild.nodeValue;
                     var payee_desc = baseResponse.getElementsByTagName("payee_desc")[k].firstChild.nodeValue;
                     var view = baseResponse.getElementsByTagName("status")[k].firstChild.nodeValue;
                     
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=payee_code;                     
                     var cell=document.createElement("TD");                     
                     if (view == "C") {
               			//var tid = document.createTextNode("Cancel");			
               			var priceSpan = document.createElement("span");
               			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
               			priceSpan.appendChild(document.createTextNode("Cancel"));			
               			cell.appendChild(priceSpan);          			
               		}else{
               			var anc=document.createElement("A");       
                        var url="javascript:loadValuesFromTable('" +payee_code+ "')"; 
                        anc.href=url;
                        var txtedit=document.createTextNode("Edit");
                        anc.appendChild(txtedit);
                        cell.appendChild(anc);          						
               		}
                     mycurrent_row.appendChild(cell);
                  
                     var cell1 =document.createElement("TD");    
                     cell1.setAttribute('align','right');
                     var tnodepayee_code=document.createTextNode(payee_code);                         
                     cell1.appendChild(tnodepayee_code);       
                     mycurrent_row.appendChild(cell1);
                             
                     var cell2 =document.createElement("TD");   
                     cell2.setAttribute('align','left');
                     var tnodepayee_desc=document.createTextNode(payee_desc);                         
                     cell2.appendChild(tnodepayee_desc);       
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
function loadValuesFromTable(payee_code)
    {         
          Minimize();
          opener.doParentEmp(payee_code);
    }

function Minimize()
{
   window.close();
   opener.window.focus();
}