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

function callServer(command)
{
    var scheduleid=document.frmListScheduleMaster.cmbScheduleid.value;
    if(scheduleid!="0")
    {
    if(command=="list")
    {
            url="../../../../../ScheduleMasterServlet.con?Command=List&txtScheduleId="+scheduleid;
            var req=getTransport();
            req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                  ListScheduleResponse(req);
                }   
                        req.send(null);
    }
    }
    
}

function ListScheduleResponse(req)
{
    if(req.readyState==4)
    {
       if(req.status==200)
       {
              var tbody=document.getElementById("Existing");
            var t=0;
            for(t=tbody.rows.length-1;t>=0;t--)
            {
                tbody.deleteRow(0);
            }
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var Command=tagCommand.firstChild.nodeValue; 
              if(Command=="List")
            {
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                var options=baseResponse.getElementsByTagName("acchead");
                for(var i=0;i<options.length;i++)
                {
                    var acchead=baseResponse.getElementsByTagName("acchead")[i].firstChild.nodeValue;
                    var scheduleid=baseResponse.getElementsByTagName("schedule")[i].firstChild.nodeValue;
                    var ind=baseResponse.getElementsByTagName("Indicator")[i].firstChild.nodeValue;
                    var accheadname=baseResponse.getElementsByTagName("accheadname")[i].firstChild.nodeValue;
                    var tbody=document.getElementById("tblList");
                    var table=document.getElementById("Existing");
                    var mycurrent_row=document.createElement("TR");
                    
                    mycurrent_row.id=i;
                    var cell1=document.createElement("TD");
                    var cell2=document.createElement("TD");
                    var cell3=document.createElement("TD");
                    var cell4=document.createElement("TD");
                    
                    var anc=document.createElement("A");       
                                    
                    var url="javascript:loadValuesFromTable('" + i + "')";              
                    anc.href=url;
                    var txtedit=document.createTextNode("Edit");
                    anc.appendChild(txtedit);
                    cell1.appendChild(anc);
                    var hidden1=document.createElement("input");
                    hidden1.type="hidden";
                    hidden1.name=i;
                    hidden1.value=i;
                    cell1.appendChild(hidden1);
                                        
                    mycurrent_row.appendChild(cell1);
                    
                    var accountunitid=document.createTextNode(accheadname+"("+acchead+")");
                    cell2.appendChild(accountunitid);
                    var hidden2=document.createElement("input");
                    hidden2.type="hidden";
                    hidden2.name="txtaccountunitid";
                    hidden2.value=acchead;
                    cell2.appendChild(hidden2);

                    mycurrent_row.appendChild(cell2);
                    
                    var officeid=document.createTextNode(scheduleid);
                    cell3.appendChild(officeid);
                    var hidden3=document.createElement("input");
                    hidden3.type="hidden";
                    hidden3.name="txtofficeid";
                    hidden3.value=scheduleid;
                    cell3.appendChild(hidden3);
                    mycurrent_row.appendChild(cell3);
                    
                    
                    
                    
                    /*var indi=document.createTextNode(ind);
                    cell4.appendChild(indi);
                    var hidden4=document.createElement("input");
                    hidden4.type="hidden";
                    hidden4.name="ind";
                    hidden4.value=ind;
                    cell4.appendChild(hidden4);
                    mycurrent_row.appendChild(cell4);*/
                    
                    tbody.appendChild(mycurrent_row);
                }
            }
       }
    }

}   

function loadValuesFromTable(id)
{
    
    Minimize();
    var r=document.getElementById(id); 
    var rcells=r.cells;
    var acchead=rcells.item(1).lastChild.value;
    var schedule=rcells.item(2).lastChild.value;
    //var ind=rcells.item(3).lastChild.value;
    opener.List(acchead,schedule);
    self.close();
}

function Minimize() 
{
window.resizeTo(0,0);
window.screenX = screen.width;
window.screenY = screen.height;
opener.window.focus();
}