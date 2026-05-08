//XMLHttpRequest Object Coding ///

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


function callServer()
{

    var accountunitid=document.frmAccountList.cmbAccountUnit.value;
    if(accountunitid!=0)
    {
    var url="../../../../../AccountingUnitServlet.con?command=List&AccountUnitId="+accountunitid;
    
    var req=getTransport();
    req.open("Post",url,true); 
    req.onreadystatechange=function()
        {
           LoadValuesResponse(req);
        }   
         req.send(null);
    }
}

function LoadValuesResponse(req)
{
    if(req.readyState==4)
    {
          if(req.status==200)
          {    
          
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                var tbody=document.getElementById("tblList");
                var table=document.getElementById("Existing");
               
                var t=0;
                for(t=tbody.rows.length-1;t>=0;t--)
                {
                    tbody.deleteRow(0);
                }
                var options=response.getElementsByTagName("leng");
                for(var i=0;i<options.length;i++)
                {
                 var mycurrent_row=document.createElement("TR");
                var cell1=document.createElement("TD");
                var cell2=document.createElement("TD");
                var cell3=document.createElement("TD");
                var cell4=document.createElement("TD");
                var cell5=document.createElement("TD");
                
                var txtoffice=response.getElementsByTagName("accountid")[i].firstChild.nodeValue;
                var txtunit=response.getElementsByTagName("unitname")[i].firstChild.nodeValue;
                var accoffice=response.getElementsByTagName("accofficeid")[i].firstChild.nodeValue;
                var accforofficeid=response.getElementsByTagName("accforofficeid")[i].firstChild.nodeValue;
                var officename=response.getElementsByTagName("officename")[i].firstChild.nodeValue;
                var anc=document.createElement("A");       
                mycurrent_row.id=i;                    
                var url="javascript:loadValuesFromTable('" + i + "')";              
                anc.href=url;
                var txtedit=document.createTextNode("Edit");
                anc.appendChild(txtedit);
                cell1.appendChild(anc);
                mycurrent_row.appendChild(cell1);
                
                txtoffice1=document.createTextNode(txtoffice);
                cell2.appendChild(txtoffice1);
                mycurrent_row.appendChild(cell2);
                var hidden=document.createElement("input");
                 hidden.type="hidden";
                 hidden.name="txtunit";
                 hidden.value=txtunit;
                 cell2.appendChild(hidden);
                 mycurrent_row.appendChild(cell2);  
                
               /* txtunit1=document.createTextNode(txtunit);
                cell3.appendChild(txtunit1);
                mycurrent_row.appendChild(cell3);
                
                accoffice1=document.createTextNode(accoffice);
                cell4.appendChild(accoffice1);
                mycurrent_row.appendChild(cell4);*/
                
                accforofficeid1=document.createTextNode(accforofficeid);
                cell3.appendChild(accforofficeid1);
                mycurrent_row.appendChild(cell3);
                
                officename=document.createTextNode(officename);
                cell4.appendChild(officename);
                mycurrent_row.appendChild(cell4);
                tbody.appendChild(mycurrent_row);
                }
          }
    }
        

}
function Minimize() 
{
//alert("called");
window.resizeTo(0,0);
window.screenX = screen.width;
window.screenY = screen.height;
opener.window.focus();
}
function loadValuesFromTable(id)
{

    Minimize();
    var r=document.getElementById(id);
    var rcells=r.cells;
    //alert("show");
    
  //  alert(rcells)
    var table=document.getElementById("tblList");
  //  alert(table);
   // alert("show");
    var rows=table.rows;
   // alert(rows);
    var value;
    var length=rows.length;
  
    var accountingunitid=rcells.item(1).firstChild.nodeValue;
    //alert(accountingunitid);
    var accountunitname=rcells.item(2).firstChild.nodeValue;
   // alert(accountunitname);
    var accountunitoffice=rcells.item(3).firstChild.nodeValue;
   // alert(accountunitoffice);
    var accountrenderoffice=rcells.item(4).firstChild.nodeValue;
   // alert(accountrenderoffice);
    var dteofopen=rcells.item(5).firstChild.nodeValue;
    //alert(dteofopen);
    opener.List(accountingunitid,accountunitname,accountunitoffice,accountrenderoffice,length,dteofopen);
    self.close();
}
