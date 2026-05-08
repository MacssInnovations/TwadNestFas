var req=false;
try
{
    req=new ActiveXObject("Msxml2.XMLHTTP");
}
catch(e)
{
    try
    {
        req=new ActiveXObject("Microsoft.XMLHTTP");
    }
    catch(ee)
    {
        req=false;
    }
}

if(!req || typeof XMLHTTPRequest !='undefined')
{
    req=new XMLHttpRequest();
}

function toExit()
{
  //window.close();
var w=window.open(window.location.href,"_self");
w.close();
}

function loadfun()
{
   document.frmusercategory.txtusercategory.focus();
}
var f='';
var cf=0;

var edcode='';
var edtype='';
function nullcheck()
{
    if((document.frmusercategory.txtusercategory.value==null)||(document.frmusercategory.txtusercategory.value.length==0))
    {
        alert("Null Value not accepted");
        document.frmusercategory.txtusercategory.focus();
        return false;
    }
    else if(!isNaN(document.frmusercategory.txtusercategory.value))
    {
        alert("Enter String value");
        document.frmusercategory.txtusercategory.value="";
        document.frmusercategory.txtusercategory.focus();
        return false;
    }
     var pscode=document.frmusercategory.txtusercategoryid.value;
    document.frmusercategory.txtusercategory.value=document.frmusercategory.txtusercategory.value.replace(/\s+/g,' ');
    var psname=document.frmusercategory.txtusercategory.value;
    psname=psname.toUpperCase();
    
    if(f=='add' )
    {
    if( !(document.frmusercategory.txtusercategoryid.value==null)||(document.frmusercategory.txtusercategoryid.value.length==0) )
    {
     var url="../../../../../UserCategoryServ.view?Command=novali&txtusercategoryid="+pscode;
     //alert(url);
     req.open("GET",url,false);
     req.onreadystatechange=handleResponse;
     if(window.XMLHttpRequest)  req.send(null);
     else req.send();
    } 
    
    var url="../../../../../UserCategoryServ.view?Command=novalitype&txtusercategoryid="+pscode+"&txtusercategory="+psname;
    req.open("GET",url,false);
    req.onreadystatechange=handleResponse;
   if(window.XMLHttpRequest)  req.send(null);
     else req.send();
 

    }
    if(f=='update')
    {
        if(edtype!=document.frmusercategory.txtusercategory.value)
        {
            var url="../../../../../UserCategoryServ.view?Command=novalitype&txtusercategoryid="+pscode+"&txtusercategory="+psname;
            req.open("GET",url,false);
            req.onreadystatechange=handleResponse;
            if(window.XMLHttpRequest)  req.send(null);
            else req.send();
        }
    }
     f='';
    return true;
}

function loadTable(scod)
{
    var r=document.getElementById(scod);
    var rcells=r.cells;
      var tbody=document.getElementById("tb");
    var table=document.getElementById("mytable");
    document.frmusercategory.txtusercategoryid.value=rcells.item(1).firstChild.nodeValue;
    document.frmusercategory.txtusercategory.value=rcells.item(2).firstChild.nodeValue;
    edcode=document.frmusercategory.txtusercategoryid.value;
    edtype=document.frmusercategory.txtusercategory.value;
    document.frmusercategory.cmdupdate.disabled=false;
    document.frmusercategory.cmddelete.disabled=false;
    document.frmusercategory.cmdadd.disabled=true;
}


function doFunction(Command,param)
{
    cf=0;
    var pscode=document.frmusercategory.txtusercategoryid.value;
   document.frmusercategory.txtusercategory.value=document.frmusercategory.txtusercategory.value.replace(/\s+/g,' ');
    var psname=document.frmusercategory.txtusercategory.value;
    psname=psname.toUpperCase();
    var flag="";
    
    if(Command=="Add")
    {
        f='add';
        var flag=nullcheck();
        if(flag==true)
        {
            var url="../../../../../UserCategoryServ.view?Command=Add&txtusercategoryid="+pscode+"&txtusercategory="+psname;
            req.open("GET",url,true);
         
            req.onreadystatechange=handleResponse;
             
           if(window.XMLHttpRequest)  req.send(null);
            else req.send();
                
        }
    }
    else if(Command=="Clear")
    {
        document.frmusercategory.txtusercategoryid.value="";
        document.frmusercategory.txtusercategory.value="";
        document.frmusercategory.txtusercategory.focus();
        document.frmusercategory.cmdadd.disabled=false;
        document.frmusercategory.cmdupdate.disabled=true;
        document.frmusercategory.cmddelete.disabled=true;
        
    }
    else if(Command=="Delete")
    {
        var flag=nullcheck();
        if(flag==true)
        {
            if(confirm("Do You Really want to Delete it?"))
            {
            var url="../../../../../UserCategoryServ.view?Command=Delete&txtusercategoryid="+pscode+"&txtusercategory="+psname;
            req.open("GET",url,true);
            req.onreadystatechange=handleResponse;
             if(window.XMLHttpRequest)  req.send(null);
            else req.send();
            }
            else
            {
               alert("Records are not Deleted");
            }
        }
   }
   else if(Command=="Update")
   {
        f='update';
        var flag=nullcheck();
        if(flag==true)
        { 
        var url="../../../../../UserCategoryServ.view?Command=Update&txtusercategoryid="+pscode+"&txtusercategory="+psname;
        req.open("GET",url,true);
        req.onreadystatechange=handleResponse;
        if(window.XMLHttpRequest)  req.send(null);
            else req.send();
        }
   }
 }

function handleResponse()
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="Add")
            {
                addRow(baseResponse);
            }
            
            else if(Command=="Delete")
            {
                deleteRow(baseResponse);
            }
            else if(Command=="Update")
            {
                UpdateRow(baseResponse);
            }
            else if(Command=="novali")
            {
                novaliRow(baseResponse);
            }
            else if(Command=="novalitype")
            {
                novaliRowType(baseResponse);
            }
            else if(Command=="clear")
            {
                ClearFun(baseResponse);
            }
        }
    }
}


function addRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
    if(flag=="success")
    {
        var id=baseResponse.getElementsByTagName("genid")[0].firstChild.nodeValue;
        document.frmusercategory.txtusercategoryid.value=id;
        alert("Records inserted into DB");
        var items=new Array();
        items[0]=document.frmusercategory.txtusercategoryid.value;
        items[1]=document.frmusercategory.txtusercategory.value.toUpperCase();;
        
        var tbody=document.getElementById("tb");
        var mycurrent_row=document.createElement("TR");
        
        mycurrent_row.id=items[0];
        var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:loadTable('"+items[0]+"')";
        anc.href=url;
        var txtedit=document.createTextNode("Edit");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
        mycurrent_row.appendChild(cell);
        var i=0;
        var cell2;
        
        for(i=0;i<2;i++)
        {
            cell2=document.createElement("TD");
            cell2.setAttribute('align','left');
            var currentText=document.createTextNode(items[i]);
            cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);
        }
        
        tbody.appendChild(mycurrent_row);
        document.frmusercategory.txtusercategoryid.value="";
        document.frmusercategory.txtusercategory.value="";
        document.frmusercategory.txtusercategory.focus();
    }
    else
    {
       if(cf==0) alert("Records r not inserted");
    }
}

function deleteRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
        alert("Records deleted from DB");
         var sc=baseResponse.getElementsByTagName("scd")[0].firstChild.nodeValue;
        var tbody=document.getElementById("mytable");
        var r=document.getElementById(sc);
        var ri=r.rowIndex;
        tbody.deleteRow(ri);
        
        document.frmusercategory.txtusercategoryid.value="";
        document.frmusercategory.txtusercategory.value="";
        document.frmusercategory.txtusercategory.focus();
        document.frmusercategory.cmdadd.disabled=false;
        document.frmusercategory.cmdupdate.disabled=true;
        document.frmusercategory.cmddelete.disabled=true;
    }
    else
    {
        alert("Records r not deleted");
    }
}

function UpdateRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var items=new Array();
    
    if(flag=="success")
    {
     edcode=document.frmusercategory.txtusercategoryid.value;
    edtype=document.frmusercategory.txtusercategory.value;
   
        items[0]=document.frmusercategory.txtusercategoryid.value;
        items[1]=document.frmusercategory.txtusercategory.value.toUpperCase();
        var r=document.getElementById(items[0]); 
        var rcells=r.cells;
       
        rcells.item(1).firstChild.nodeValue=items[0];
        
        rcells.item(2).firstChild.nodeValue=items[1];
        alert("Record Updated");
    }
    else
    {
      if(cf==0)  alert("Record not Updated");
    }
}



function novaliRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
       //alert("No Duplication ID..Can proceed");
    }
    else
    {
      alert("User Category Code already exists.");
      cf=1;
       document.frmusercategory.txtusercategoryid.value="";
       // document.frmusercategory.txtusercategoryid.focus();
       document.frmusercategory.cmdclear.focus();
        return false;
    }
}

function novaliRowType(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
      // alert("No Duplication Type..Can proceed");
    }
    else
    {
        if (cf==0)alert("User Category already exists");
       cf=1;
        document.frmusercategory.txtusercategory.value="";
        document.frmusercategory.txtusercategory.focus();
         return false;
    }
}


function ClearFun(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var id=baseResponse.getElementsByTagName("genid")[0].firstChild.nodeValue;
    if(flag=="success")
    {
     document.frmusercategory.txtusercategoryid.value=id;
    document.frmusercategory.txtusercategory.focus();
    }
}
/*
function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode
        if (unicode!=8)
        {
            if (unicode<48||unicode>57) 
                return false 
        }
    }
/**/   
     function nonanumber(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode
        if (unicode!=8)
        {
            if ((unicode>=48 && unicode<=57) || unicode==32 || (unicode>=65 && unicode<=90) || (unicode>=97 && unicode<=122) || unicode==45 || unicode==95 )
                return true;
            else
                return false;
        }
    }