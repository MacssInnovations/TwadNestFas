//XMLHttpRequest Object Created
 var sequence=0;
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

function listAll()
{

                        var tbl=document.getElementById("tblList");
                         var i;
                                  
                          for(i=tbl.rows.length;i>0;i--)
                          {        
                            tbl.deleteRow(0);
                          }   

    url="../../../../../ServletLoadRegFees.view?command=Display";
    var req=getTransport();
    req.open("GET",url,true);        
    req.onreadystatechange=function()
            {
               processResponse(req);
            }   
    req.send(null);
     var tbody=document.getElementById("tblList1");

}

function processResponse(req)
           {
               // code for processing the xml returned by servlet  
          if(req.readyState==4)
          {
          if(req.status==200)
          {              
              
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
           
              //alert(req.responseText);
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 

              if(command=="Display")
              {
                displayDetails(baseResponse);
              
              }
         }
     }
}

function displayDetails(baseResponse)
    {
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
                  {
                  //getting the number of rows 
                                    
                  var value=baseResponse.getElementsByTagName("value");
                  
                  var tbody=document.getElementById("tblList");
                  var j=0;
                  var k=0;
                  
                  
                        for(k=0;k<value.length;k++)
                        {
                       sequence=sequence+1;
                       var items=new Array();
                        var offid1=value[k].getElementsByTagName("offid")[0].firstChild.nodeValue;
                        var offdesc1=value[k].getElementsByTagName("offdesc")[0].firstChild.nodeValue;
                        var cid1=value[k].getElementsByTagName("cid")[0].firstChild.nodeValue;
                        var cdesc1=value[k].getElementsByTagName("cdesc")[0].firstChild.nodeValue;
                        var state1=value[k].getElementsByTagName("state")[0].firstChild.nodeValue;
                        var fees1 =value[k].getElementsByTagName("fees")[0].firstChild.nodeValue;
                        var date1=value[k].getElementsByTagName("date")[0].firstChild.nodeValue;
                        
                        var mycurrent_row=document.createElement("TR");
                         mycurrent_row.id=sequence;
                          
                          var cell1=document.createElement("TD");
                          //serial number generation
                         //appending the sno
                         //sequence=0;
                           
                           var sno=document.createTextNode("" + sequence);  
                           cell1.appendChild(sno);  
                           mycurrent_row.appendChild(cell1);
                           
                           
                           var cell2=document.createElement("TD");//CLASS ID AND DESC
                           var toid=document.createElement("INPUT");
                           var todesc=document.createTextNode(offdesc1);
                            toid.type="HIDDEN";
                           toid.text=offid1;
                           toid.value=offid1;
                           toid.name=offid1;
                           cell2.appendChild(toid);
                           cell2.appendChild(todesc);
                           mycurrent_row.appendChild(cell2);
                           
                         
                           
                           var cell3=document.createElement("TD");//CLASS ID AND DESC
                           var tcid=document.createElement("INPUT");
                           var tcdesc=document.createTextNode(cdesc1);
                           tcid.type="HIDDEN";
                           tcid.text=cid1;
                           tcid.value=cid1;
                           tcid.name=cid1;
                           cell3.appendChild(tcid);
                           cell3.appendChild(tcdesc);
                           mycurrent_row.appendChild(cell3);
                           
                           var cell4=document.createElement("TD");//STATE
                           var tstate=document.createTextNode(state1);
                           cell4.appendChild(tstate);
                           mycurrent_row.appendChild(cell4);
                           
                           var cell5=document.createElement("TD");//FEES
                           var tfees=document.createTextNode(fees1);
                           cell5.appendChild(tfees);
                           mycurrent_row.appendChild(cell5);
                           
                           var cell6=document.createElement("TD");//DATE
                           var tdate=document.createTextNode(date1);
                           cell6.appendChild(tdate);
                           mycurrent_row.appendChild(cell6);
                           
                           
                           tbody.appendChild(mycurrent_row); 
                           }

                  }
                else
                   {
                   alert("Sorry Failed to Load and Display the values");
                   }
     }   
