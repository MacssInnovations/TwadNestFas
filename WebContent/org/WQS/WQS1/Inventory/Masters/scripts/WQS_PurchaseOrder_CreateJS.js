
var seq=0,cou=0;
var line=0,lcou=0;
function getTransport()
{
var req=false;
try
{
req=new ActiveXObject("Msxml2.XMLHTTP");
}catch(e1)
 {
    try{
    req=new ActiveXObject("Microsoft.XMLHTTP");
    }
    catch(e2)
    {
    req=false;
    }
 }
    if(!req && typeof XMLHttpRequest!='undefined')
        {
        req=new XMLHttpRequest();
        }
   return req;
}    


function processResponse(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var val=baseResponse.getElementsByTagName("command")[0];
            var cmd=val.firstChild.nodeValue;
            InsertDetails(baseResponse);
        }
    }
}

function nullCheck()
{
                 
                  if((document.PurchaseOrder.orderno.value=="") || (document.PurchaseOrder.orderno.value.length<=0))
                  {
                       alert("Dont leave Purchase Order Number Field Empty");
                       return false;
                  }
                  if((document.PurchaseOrder.orderdate.value=="") || (document.PurchaseOrder.orderdate.value.length<=0))
                  {
                       alert("Dont leave Date Field Empty");
                       return false;
                  }
                  if((document.PurchaseOrder.sup.value=="") || (document.PurchaseOrder.sup.value.length<=0))
                  {
                       alert("Dont leave Supplier Field Empty");
                       return false;
                  }
                  return true;
}

function rowDetails(a,b,c,d,f,e)
{
                 var tbody=document.getElementById("tblList");
                 var table=document.getElementById("Existing");
                 seq=tbody.rows.length;
                 seq=seq+1;                         
                 var mycurrent_row=document.createElement("TR");
                 mycurrent_row.id=seq;
                 
                 var cell1 =document.createElement("TD"); 
                 var hidden1="";
                 if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                 {
                        hidden1=document.createElement("<input type='radio' name='sel' value="+seq+">");
                 }
                 else
                 {    
                       hidden1=document.createElement("input");     // serial number generation
                       hidden1.type="radio";             
                       hidden1.name="sel";
                       hidden1.value=seq;
                 }
                 cell1.appendChild(hidden1);
                 cell1.setAttribute("align","center");
                 mycurrent_row.appendChild(cell1);
                 
                 var sno =document.createElement("TD");   
                 var no=document.createTextNode(seq);                        
                 sno.appendChild(no);  
                 mycurrent_row.appendChild(sno);  
                 
                 var cell2 =document.createElement("TD");   
                 var cat=document.createTextNode(a);                        
                 cell2.appendChild(cat);  
                 mycurrent_row.appendChild(cell2);   
                         
                         
                 var cell3 =document.createElement("TD");   
                 var icode=document.createTextNode(b);   
                 cell3.appendChild(icode);   
                 mycurrent_row.appendChild(cell3);
                        
                 var cell4 =document.createElement("TD");   
                 var idesc=document.createTextNode(c);                        
                 cell4.appendChild(idesc);
                 mycurrent_row.appendChild(cell4);
                                               
                 var cell5 =document.createElement("TD");   
                 var qty=document.createTextNode(d);                        
                 cell5.appendChild(qty);     
                 mycurrent_row.appendChild(cell5);
                 
                 var cell7 =document.createElement("TD");   
                 var uom=document.createTextNode(f);                        
                 cell7.appendChild(uom);     
                 mycurrent_row.appendChild(cell7);
                 
                 var cell6 =document.createElement("TD");   
                 var rem=document.createTextNode(e);                        
                 cell6.appendChild(rem);     
                 mycurrent_row.appendChild(cell6);
                
                 tbody.appendChild(mycurrent_row); 
                 
}


//////////////   FOR PURCHASE ORDER POPUP WINDOW //////////////////////
var winemp;
function servicepopup()
{
    var flag=nullCheck();
    if(flag==true)
    {
        var lb=document.PurchaseOrder.lab.value;
        var lab=lb.split("--");
        var orderno=document.PurchaseOrder.orderno.value;
        var sup=document.PurchaseOrder.sup.value;
        var scode=sup.split("--");
        if (winemp && winemp.open && !winemp.closed) 
        {
           winemp.resizeTo(500,600);
           winemp.moveTo(200,200); 
           winemp.focus();
           return ;
        }
        else
        {
            winemp=null
        }
            
        winemp= window.open("../../../../../../org/WQS/WQS1/Inventory/Masters/jsps/WQS_PurchaseOrder_Popup.jsp?labcode="+lab[0]+"&ono="+orderno+"&sup="+scode[0],"ItemSelection","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
        winemp.moveTo(250,250);  
        winemp.focus();
    }
}

function doParentVal(catcode,item1,item2,order,uom,remarks)
{
  rowDetails(catcode,item1,item2,order,uom,remarks);
}

///////////////////////////////////////////////////////////////////////////////////

function onSubmit()
{
            var tb=document.getElementById("tblList");
            var t=tb.rows.length 
            if(t>0)
           {
            var lb=document.PurchaseOrder.lab.value;
            var lab=lb.split("--");
            var orderno=document.PurchaseOrder.orderno.value;
            var orderdate=document.PurchaseOrder.orderdate.value;
            var sup=document.PurchaseOrder.sup.value;
            var supplier=sup.split("--");
            var remarks=document.PurchaseOrder.remarks.value;
            if(remarks=="")
                remarks="-";
            var record=null;
            var tbody=document.getElementById("tblList");
            var table=document.getElementById("Existing");           
            var rid=0;
            record1=new Array();
            record2=new Array();
            record3=new Array();
            record4=new Array();
            record5=new Array();
            record6=new Array();
            record7=new Array();
            var v=document.getElementsByName("sel");
            for(var i=1;i<=tbody.rows.length;i++)
            {
                rid=i;
                var r=document.getElementById(rid);
                var rcells=r.cells;
                record1[i]=rcells.item(1).firstChild.nodeValue;
                record2[i]=rcells.item(2).firstChild.nodeValue;
                record3[i]=rcells.item(3).firstChild.nodeValue;
                record4[i]=rcells.item(4).firstChild.nodeValue;
                record5[i]=rcells.item(5).firstChild.nodeValue;
                record6[i]=rcells.item(6).firstChild.nodeValue; 
                record7[i]=rcells.item(7).firstChild.nodeValue; 
                if(i==1)
                {
                    record=record2[i]+"//"+record3[i]+"//"+record4[i]+"//"+record5[i]+"//"+record6[i]+"//"+record7[i];
                }
                else
                {
                    record=record+",,"+record2[i]+"//"+record3[i]+"//"+record4[i]+"//"+record5[i]+"//"+record6[i]+"//"+record7[i];
                }
            }
                var url="../../../../../../WQS_PurchaseOrder_Create?command=submit&lcode="+lab[0]+"&ono="+orderno+"&odate="+orderdate+"&scode="+supplier[0]+"&remarks="+remarks+"&record="+record+"&lcou="+lcou;
                var req=getTransport();
                req.open("GET",url,true);
                req.onreadystatechange=function()
                {
                    processResponse(req);
                }  
                req.send(null);
            }
            else
            {
                alert("Enter Item Details");
            }
} 

function InsertDetails(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="Success")
    {
        var fg=baseResponse.getElementsByTagName("fg")[0].firstChild.nodeValue;
        if(fg=='Success')
        {
            alert("Record inserted successfully");
            clearDet();
        }
        else 
        {
            alert("Failed to insert a value");
        }
    }
    else
    {
        alert("failed to insert a value");
    }
   
}

function updateDet()
{
    var v=document.getElementsByName("sel");
    if(v)
    {
        for(i=0;i<v.length;i++)
        {
            if(v[i].checked==true)
            {
                var rid=v[i].value;
                var r=document.getElementById(rid);
                var rcells=r.cells;
                var tbody=document.getElementById("tblList");
                var table=document.getElementById("Existing");
                var cat=rcells.item(2).firstChild.nodeValue;
                var icode=rcells.item(3).firstChild.nodeValue;
                var idesc=rcells.item(4).firstChild.nodeValue;
                var qty=rcells.item(5).firstChild.nodeValue;
                var uom=rcells.item(6).firstChild.nodeValue;
                var rem=rcells.item(7).firstChild.nodeValue; 
                var sup=document.PurchaseOrder.sup.value;
                var scode=sup.split("--");
                var lb=document.PurchaseOrder.lab.value;
                var lab=lb.split("--");
                var winemp;
                winemp=window.open("../../../../../../org/WQS/WQS1/Inventory/Masters/jsps/WQS_PurchaseOrder_Update.jsp?lab="+lab[0]+"&sno="+rid+"&Supno="+scode[0]+"&cat="+cat+"&icode="+icode+"&idesc="+idesc+"&qty="+qty+"&uom="+uom+"&rem="+rem,"OrderUpdate","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
                winemp.moveTo(250,250);  
                winemp.focus();
            }
        }
    }
}

function doParentUpd(sno,catcode,item1,item2,order,uom,remarks)
{
   // updateDetails(sno,catcode,item1,item2,order,remarks); 
               var r=document.getElementById(sno); 
               var rcells=r.cells;
               
               rcells.item(2).firstChild.nodeValue=catcode;
               rcells.item(3).firstChild.nodeValue=item1;
               rcells.item(4).firstChild.nodeValue=item2;
               rcells.item(5).firstChild.nodeValue=order;
               rcells.item(6).firstChild.nodeValue=uom;     
               rcells.item(7).firstChild.nodeValue=remarks;         
}

function delDet()
{
    var v=document.getElementsByName("sel");
    var item1=new Array();
    var item2=new Array();
    var item3=new Array();
    var item4=new Array();
    var item5=new Array();
    var j=0;
    if(v)
    {
        for(var i=0;i<v.length;i++)
        {
            if(v[i].checked==false)
            {
                var oval=v[i].value;
                var val=document.getElementById(oval);
                var rcells=val.cells;
                item1[j]=rcells.item(2).firstChild.nodeValue;
                item2[j]=rcells.item(3).firstChild.nodeValue;
                item3[j]=rcells.item(4).firstChild.nodeValue;
                item4[j]=rcells.item(5).firstChild.nodeValue;
                item5[j]=rcells.item(6).firstChild.nodeValue;
                j++;
            } 
         }
            var tb=document.getElementById("tblList");
            var t=tb.rows.length   
            for(var i=t-1;i>=0;i--)
            {
                  tb.deleteRow(i);
            } 
            for(var i=0;i<j;i++)
            {
                   rowDetails(item1[i],item2[i],item3[i],item4[i],item5[i]);  
            }
        
    }
}
function clearDet()
{
            var tbody=document.getElementById("tblList");
            var table=document.getElementById("Existing");
            var t=0;
            for(t=tbody.rows.length-1;t>=0;t--)
            {
                  tbody.deleteRow(t);
            }  
            document.PurchaseOrder.orderno.value="";
            document.PurchaseOrder.orderdate.value="";
            document.PurchaseOrder.sup.selectedIndex=0;
            document.PurchaseOrder.remarks.value="";
            seq=0;
}

function checkAvail()
{
    var lcode=document.PurchaseOrder.lab.value;
    var lab=lcode.split("--");
    var ono=document.PurchaseOrder.orderno.value;
    url="../../../../../../WQS_PurchaseOrder_Create?command=checkAvail&LabCode="+lab[0]+"&OrderNo="+ono;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        Response(req);
    }  
    req.send(null);   
}

function Response(req)
{
    if(req.readyState==4)
    {
          if(req.status==200)
          {    
              var baseresponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseresponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue;
              checkDuplicate(baseresponse);
           }
    }       
}

function checkDuplicate(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=='failure')
    {
        alert("Purchase Order already exists");
        document.PurchaseOrder.orderno.value="";
        document.PurchaseOrder.orderno.focus();
    }
}

function capitalise()
{
    var orderno=document.getElementById("orderno").value;
    caporder=orderno.toUpperCase();
    document.getElementById("orderno").value=caporder;
}















