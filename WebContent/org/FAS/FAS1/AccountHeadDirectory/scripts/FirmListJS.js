
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
function loadTabAll(cmd)
{
            // var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            //var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            
             var doc=window.opener.document;
             var cmbAcc_UnitCode=doc.firmsForm.cmbAcc_UnitCode.value;
             var cmbOffice_code=doc.firmsForm.comOffId.value;
              
            var url="../../../../../firmListServ.view?cmd=ListAll&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
            //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                    {
                        handleResponse(req);
                    }   
                     req.send(null);
}


function loadTable(cmd,scod)
{
        Id=scod;
        // var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            //var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            
             var doc=window.opener.document;
             var cmbAcc_UnitCode=doc.firmsForm.cmbAcc_UnitCode.value;
             var cmbOffice_code=doc.firmsForm.comOffId.value;
              
          var url="../../../../../firmListServ.view?cmd=list&scod="+scod+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;;
            //alert(url);
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
              var Command=tagCommand.firstChild.nodeValue; 
            //alert(Command);
            if(Command=="list")
            {
                listRow(baseResponse);
            }
            else if(Command="loadTabAll")
            {
                loadTabAllRow(baseResponse);
            }
            
}
}
}
function loadvalues(scod)
{
    //alert("here");
    var r=document.getElementById(scod);
    var rcells=r.cells;
    var tbody=document.getElementById("tb");
    var table=document.getElementById("mytable");
    
        
        document.firmsForm.txtSuppId.value=rcells.item(1).firstChild.nodeValue;
        document.firmsForm.txtSuppName.value=rcells.item(2).firstChild.nodeValue;
        document.firmsForm.txtaddr.value=rcells.item(3).firstChild.nodeValue;
        document.firmsForm.txtEmail.value=rcells.item(4).firstChild.nodeValue;
        document.firmsForm.txtFax.value=rcells.item(6).firstChild.nodeValue;
        document.firmsForm.txtPhone.value=rcells.item(5).firstChild.nodeValue;
        document.firmsForm.txtPhone.value=rcells.item(7).firstChild.nodeValue;
}



function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   var tbody=document.getElementById("tb");
                        var t=0;
                        
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
    
    if(flag=="success")
    {
        var j=0;
        var supId=baseResponse.getElementsByTagName("firmsID");
       var len=supId.length;
    
     for(j=0;j<len;j++)
     {
        //alert(len);
        var supId=baseResponse.getElementsByTagName("firmsID");
        var supName=baseResponse.getElementsByTagName("firmsName");
        var supAddr=baseResponse.getElementsByTagName("supAddr");
        var supPhone=baseResponse.getElementsByTagName("supPhone");
        var supFax=baseResponse.getElementsByTagName("supFax");
        var supEmail=baseResponse.getElementsByTagName("supemail");
        var supaddr1=baseResponse.getElementsByTagName("supaddr1");
        var supcity=baseResponse.getElementsByTagName("city");
        var status=baseResponse.getElementsByTagName("status");
        
      
        var supId1=supId.item(j).firstChild.nodeValue;
        var supName1=supName.item(j).firstChild.nodeValue;
        var supAddr1=supAddr.item(j).firstChild.nodeValue;
        var supPhone1=supPhone.item(j).firstChild.nodeValue;
        var supFax1=supFax.item(j).firstChild.nodeValue;
        var supEmail1=supEmail.item(j).firstChild.nodeValue;
        var supAddr12=supaddr1.item(j).firstChild.nodeValue;
        var supcity1=supcity.item(j).firstChild.nodeValue;
         var status1=status.item(j).firstChild.nodeValue;
   
         var items=new Array();
        
        
        items[0]=supId1;
        items[1]=supName1;
        items[2]=supAddr1;
        items[3]=supAddr12;
        items[4]=supcity1;
        items[5]=supEmail1;
        items[6]=supPhone1;
        items[7]=supFax1;
        items[8]=status1
        if(items[2]=="null")
        {
        items[2]="";
        }
        if(items[3]=="null")
        {
        items[3]="";
        }
        if(items[5]=="null")
        {
        items[5]="";
        }
        if(items[6]=="null")
        {
        items[6]="";
        }
        if(items[7]=="null")
        {
        items[7]="";
        }
       if(items[4]=="null")
        {
        items[4]="";
        }
      
                    
        var mycurrent_row=document.createElement("TR");
        mycurrent_row.id=items[0];
        var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:call_Opener('"+items[0]+"')";
        anc.href=url;
        var txtedit=document.createTextNode("EDIT");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
        mycurrent_row.appendChild(cell);
    
        var i=0;
        var cell2;
        
        for(i=0;i<2;i++)
        {
            cell2=document.createElement("TD");
            var currentText=document.createTextNode(items[i]);
            cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);
        }
        
        var cell3;
        cell3=document.createElement("TD");
        var addr=document.createTextNode(items[2]+","+items[3]);
        cell3.appendChild(addr);
            mycurrent_row.appendChild(cell3);
            
          for(i=4;i<9;i++)
        {
            cell2=document.createElement("TD");
            var currentText=document.createTextNode(items[i]);
            cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);
        }  
        
        tbody.appendChild(mycurrent_row);
        }
       
    }
    else
    {
        var tbody=document.getElementById("tb");
                        var t=0;
                        
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
        alert("Records not found");
    }
    
    
}

function loadTabAllRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   var tbody=document.getElementById("tb");
                        var t=0;
                        
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
    
    if(flag=="success")
    {
        var j=0;
        var supId=baseResponse.getElementsByTagName("firmsID");
        var supName=baseResponse.getElementsByTagName("firmsName");
        var supAddr=baseResponse.getElementsByTagName("supAddr");
        var supPhone=baseResponse.getElementsByTagName("supPhone");
        var supFax=baseResponse.getElementsByTagName("supFax");
        var supEmail=baseResponse.getElementsByTagName("supemail");
        var supaddr1=baseResponse.getElementsByTagName("supaddr1");
        var supcity=baseResponse.getElementsByTagName("city");
        var status=baseResponse.getElementsByTagName("status");
        
        var len=supId.length;
    
    
     for(j=0;j<len;j++)
     {
        //alert(len);
        var supId=baseResponse.getElementsByTagName("firmsID");
        var supName=baseResponse.getElementsByTagName("firmsName");
        var supAddr=baseResponse.getElementsByTagName("supAddr");
        var supPhone=baseResponse.getElementsByTagName("supPhone");
        var supFax=baseResponse.getElementsByTagName("supFax");
        var supEmail=baseResponse.getElementsByTagName("supemail");
        var supaddr1=baseResponse.getElementsByTagName("supaddr1");
        var supcity=baseResponse.getElementsByTagName("city");
        var status=baseResponse.getElementsByTagName("status");
      //  alert(status);
      
        var supId1=supId.item(j).firstChild.nodeValue;
        var supName1=supName.item(j).firstChild.nodeValue;
        var supAddr1=supAddr.item(j).firstChild.nodeValue;
        var supPhone1=supPhone.item(j).firstChild.nodeValue;
        var supFax1=supFax.item(j).firstChild.nodeValue;
        var supEmail1=supEmail.item(j).firstChild.nodeValue;
        var supAddr12=supaddr1.item(j).firstChild.nodeValue;
        var supcity1=supcity.item(j).firstChild.nodeValue;
        var status1=status.item(j).firstChild.nodeValue;
       // alert(status1);
         var items=new Array();
        
        
         items[0]=supId1;
        items[1]=supName1;
        items[2]=supAddr1;
        items[3]=supAddr12;
        items[4]=supcity1;
        items[5]=supEmail1;
        items[6]=supPhone1;
        items[7]=supFax1;
        items[8]=status1;
       
         var mycurrent_row=document.createElement("TR");
        
        mycurrent_row.id=items[0];
       
        var i=0;
        var cell2;
        
        for(i=0;i<2;i++)
        {
            cell2=document.createElement("TD");
            var currentText=document.createTextNode(items[i]);
            cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);
        }
        
        var cell3;
        cell3=document.createElement("TD");
        var addr=document.createTextNode(items[2]+","+items[3]);
        cell3.appendChild(addr);
            mycurrent_row.appendChild(cell3);
            
          for(i=4;i<9;i++)
        {
            cell2=document.createElement("TD");
            var currentText=document.createTextNode(items[i]);
            cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);
        }  
        
        tbody.appendChild(mycurrent_row);
        }
       
    }
    else
    {
        var tbody=document.getElementById("tb");
                        var t=0;
                        
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
        alert("Records not found");
    }
    
    
}


function call_Opener(id)
{
Minimize();
opener.ParentFirm(id);
//self.close();
}


function Minimize() 
{
window.resizeTo(0,0);
window.screenX = screen.width;
window.screenY = screen.height;
opener.window.focus();
}