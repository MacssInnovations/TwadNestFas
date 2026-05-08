function checknull()
{
 
    if((document.getElementById("txtCB_Year").value.length!=4)|| (document.getElementById("txtCB_Year").value.length<=0) ||(document.getElementById("txtCB_Year").value==""))
    {
        alert("Enter the correct year");
        document.getElementById("txtCB_Year").focus();
        return false;
    }
     if(document.getElementById("txtCB_Month").value=="")
    {
        alert("Select a month");
        return false;
    }
    /*if((document.frmGeneralLedgerSystem.cmbAccHeadCode.value=="") || (document.frmGeneralLedgerSystem.cmbAccHeadCode.value.length<=0) || (document.frmGeneralLedgerSystem.cmbAccHeadCode.value=="0"))
    {
        alert("Please Select Account Head Code");
        document.frmGeneralLedgerSystem.cmbAccHeadCode.focus();
        return false;
    }*/
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
/**
 *  Browser Indentification 
 */

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
 

function callServer_LoadListGL()
{     
		//alert("GL function****");
	    var cashbook_month = document.getElementById("txtCB_Month").value;    
		var cashbook_year = document.getElementById("txtCB_Year").value;   
      var url="../../../../../ListVerifiedGLSL?Command=loadVerifiedGL&cashbook_month="+cashbook_month+"&cashbook_year="+cashbook_year;
      var req=getTransport();
      req.open("GET",url,true); 
      req.onreadystatechange=function()
      {
         handleResponse(req);
      }   
      req.send(null);
}
function callServer_LoadListSL()
{     
		var cashbook_month = document.getElementById("txtCB_Month").value;    
		var cashbook_year = document.getElementById("txtCB_Year").value;   
      var url="../../../../../ListVerifiedGLSL?Command=loadVerifiedSL&cashbook_month="+cashbook_month+"&cashbook_year="+cashbook_year;
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
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              if(command=="loadVerifiedGL")
              {
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="Success")
                {
                   var tbody=document.getElementById("grid_body");
                   var t=0;
                   for(t=tbody.rows.length-1;t>=0;t--)
                     {
                          tbody.deleteRow(0);
                     }
                   var RecNo=baseResponse.getElementsByTagName("accunitid");
                   var items=new Array();
                   var sl_no=1;
                   for(var k=0;k<RecNo.length;k++)
                     {
                	   items[0]=baseResponse.getElementsByTagName("accunitid")[k].firstChild.nodeValue;  
                       items[1]=baseResponse.getElementsByTagName("accunitname")[k].firstChild.nodeValue;   
                       items[2]=baseResponse.getElementsByTagName("gldate")[k].firstChild.nodeValue;
                       items[3]=baseResponse.getElementsByTagName("moncb")[k].firstChild.nodeValue;
                       
                      
                       var mycurrent_row=document.createElement("TR");
                       	  var cell2;
	                      cell2=document.createElement("TD");
	                      cell2.style.textAlign='left';
	                      var currentText=document.createTextNode(sl_no);
	                       cell2.appendChild(currentText);
	                       mycurrent_row.appendChild(cell2);
                       
                       	  var cell2;
	                      cell2=document.createElement("TD");
	                      cell2.style.textAlign='left';
	                      var currentText=document.createTextNode(items[0]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                          
                          cell2=document.createElement("TD");
	                      cell2.style.textAlign='left';
	                      var currentText=document.createTextNode(items[1]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                 
	                      cell2=document.createElement("TD");
	                      cell2.style.textAlign='left';
	                      var currentText=document.createTextNode(items[2]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                          
                          cell2=document.createElement("TD");
	                      cell2.style.textAlign='right';
	                      var currentText=document.createTextNode(items[3]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                          sl_no++;                        
                          tbody.appendChild(mycurrent_row);
                      }
                  
                }
                else if(flag=='Failure')
                {
                  alert("Data not found ");
                }
              }
              else if(command=="loadVerifiedSL")
              {
//                alert("load SL ");
            	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="Success")
                {
                   var tbody=document.getElementById("grid_body");
                   var t=0;
                   for(t=tbody.rows.length-1;t>=0;t--)
                     {
                          tbody.deleteRow(0);
                     }
                   var RecNo=baseResponse.getElementsByTagName("accunitid");
                   var items=new Array();
                   var sno=1;
                   for(var k=0;k<RecNo.length;k++)
                     {
                	   items[0]=baseResponse.getElementsByTagName("accunitid")[k].firstChild.nodeValue;  
                       items[1]=baseResponse.getElementsByTagName("accunitname")[k].firstChild.nodeValue;   
                       items[2]=baseResponse.getElementsByTagName("sldate")[k].firstChild.nodeValue;
                       items[3]=baseResponse.getElementsByTagName("moncb")[k].firstChild.nodeValue;
                         var mycurrent_row=document.createElement("TR");
                         var cell2;
	                      cell2=document.createElement("TD");
	                      cell2.style.textAlign='left';
	                      var currentText=document.createTextNode(sno);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                         
                         var cell2;
	                      cell2=document.createElement("TD");
	                      cell2.style.textAlign='left';
	                      var currentText=document.createTextNode(items[0]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                          
                          cell2=document.createElement("TD");
	                      cell2.style.textAlign='left';
	                      var currentText=document.createTextNode(items[1]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                 
	                      cell2=document.createElement("TD");
	                      cell2.style.textAlign='left';
	                      var currentText=document.createTextNode(items[2]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                          
                          cell2=document.createElement("TD");
	                      cell2.style.textAlign='right';
	                      var currentText=document.createTextNode(items[3]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                          
                          sno++;
                          tbody.appendChild(mycurrent_row);
                     }
                }
                else if(flag=='Failure')
                {
                  alert("Data not found ");
                }
              }
          }
        }
}




     