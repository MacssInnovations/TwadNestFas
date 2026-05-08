//alert("sdfsdfsdf");
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

///////////////loading values from combo box
function listcombo()
{
var majorid=document.Opening_bal_list.cmbMajor_grp.value;
var minorid=document.Opening_bal_list.cmbMinor_grp.value;
var sub1id=document.Opening_bal_list.cmbSub_grp1.value;
var sub2id=document.Opening_bal_list.cmbSub_grp2.value;

var url="../../../../../Opening_balance_ListServ.view?cmd=listcombo&cmbMajor_grp="+majorid+"&cmbMinor_grp="+minorid+"&cmbSub_grp1="+sub1id+"&cmbSub_grp2="+sub2id;
            //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                    {
                        handleResponse(req);
                    }   
                     req.send(null);



}
//////////////////end


////////////////loading minor values
function loadminor()
{
var majorid=document.Opening_bal_list.cmbMajor_grp.value;
 var url="../../../../../Opening_balance_ListServ.view?cmd=loadminor&majorid="+majorid;
            //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                    {
                        handleMinorRes(req);
                    }   
                     req.send(null);
}
function  handleMinorRes(req)
{
if(req.readyState==4)
{
  if(req.status==200)
   {
    var i;
   var j;
   var first=document.getElementById("cmbMinor_grp");
   first.innerHTML="";
   
   var sel=req.responseXML.getElementsByTagName("select")[0];
   
   var options=sel.getElementsByTagName("option");
   var htop=document.createElement("OPTION");
    htop.text="--Select--";
    try
    {
    first.add(htop);
    }
    catch(e)
    {
    first.add(htop,null);
    }
   for(i=0;i<options.length;i++)
   {
   
    var desc=options[i].getElementsByTagName("minorDesc")[0].firstChild.nodeValue;
   var id=options[i].getElementsByTagName("minorcode")[0].firstChild.nodeValue;
   var htoption=document.createElement("OPTION");
   htoption.text=desc+"("+id+")";
   htoption.value=id;
   try
   {
    first.add(htoption);
   }
   catch(e)
   {
     first.add(htoption,null);
   }
}

}
}
}
/////////////////////end

////////////////loading sub group values
function loadsub()
{
var minorid=document.Opening_bal_list.cmbMinor_grp.value;

 var url="../../../../../Opening_balance_ListServ.view?cmd=loadsub&minorid="+minorid;
           // alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                    {
                        handleOutputSubGrp1(req);
                    }   
                     req.send(null);
}

function  handleOutputSubGrp1(req)
{
if(req.readyState==4)
{
  if(req.status==200)
   {
   var i;
   var j;
   var first=document.getElementById("cmbSub_grp1");
   first.innerHTML="";
   
   var second=document.getElementById("cmbSub_grp2");
   second.innerHTML="";
   
   var sel=req.responseXML.getElementsByTagName("select")[0];
   
   var options=sel.getElementsByTagName("option");
var options1=sel.getElementsByTagName("option");
   
   var htop=document.createElement("OPTION");
    htop.text="--Select--";
    
   var htop1=document.createElement("OPTION");
   htop1.text="--Select--";
 
 
    try
    {
    first.add(htop);
    second.add(htop1);
    }
    catch(e)
    {
    first.add(htop,null);
    second.add(htop1,null);
    }
    
   for(i=0;i<options.length;i++)
   {
   
    var desc=options[i].getElementsByTagName("subgrpDesc1")[0].firstChild.nodeValue;
   var id=options[i].getElementsByTagName("subgrp1")[0].firstChild.nodeValue;
   var htoption=document.createElement("OPTION");
   htoption.text=desc+"("+id+")";
   htoption.value=id;
   try
   {
    first.add(htoption);
   }
   catch(e)
   {
     first.add(htoption,null);
   }
}
for(i=0;i<options1.length;i++)
   {
   
    var desc=options1[i].getElementsByTagName("subgrpDesc1")[0].firstChild.nodeValue;
   var id=options1[i].getElementsByTagName("subgrp1")[0].firstChild.nodeValue;
   var htoption=document.createElement("OPTION");
   htoption.text=desc+"("+id+")";
   htoption.value=id;
   try
   {
    second.add(htoption);
   }
   catch(e)
   {
     second.add(htoption,null);
   }
}
   
   
}
}
}
/////////////////////end

function loadTable(cmd,scod)
{
       
        var url="../../../../../Opening_balance_ListServ.view?cmd=list&scod="+scod;
            //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                    {
                        handleResponse(req);
                    }   
                     req.send(null);
}
function listRange()
{
if((document.Opening_bal_list.txtFrom.value==null)||(document.Opening_bal_list.txtFrom.value.length==0))
{
if((document.Opening_bal_list.txtTo.value==null)||(document.Opening_bal_list.txtTo.value.length==0))
    {
        alert("Null Value not accepted....Enter To Value");
        document.Opening_bal_list.txtTo.focus();
        
    }
    alert("Null Value not accepted....Enter From Value");
     document.Opening_bal_list.txtFrom.focus();
}
else
{
var from=document.Opening_bal_list.txtFrom.value;
var to=document.Opening_bal_list.txtTo.value;

        var url="../../../../../Opening_balance_ListServ.view?cmd=listRange&txtFrom="+from+"&txtTo="+to;
           // alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                    {
                        handleResponse(req);
                    }   
                     req.send(null);
}
}

function loadTableNO(cmd,scod)
{
       
        var url="../../../../../Opening_balance_ListServ.view?cmd=listNO&scod="+scod;
           // alert(url);
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
           // alert(Command);
            if(Command=="list")
            {
                listRow(baseResponse);
            }
            
            else if(Command=="listRange")
            {
                listRangeRow(baseResponse);
            }
                       
}
}
}


function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
    /////////////////for text box display////////////////////////
    var majorDesc=baseResponse.getElementsByTagName("majorDesc")[0].firstChild.nodeValue;
    var minordesc=baseResponse.getElementsByTagName("minordesc")[0].firstChild.nodeValue;
    var subdesc1=baseResponse.getElementsByTagName("subdesc1")[0].firstChild.nodeValue;
    var subdesc2=baseResponse.getElementsByTagName("subdesc2")[0].firstChild.nodeValue;
     document.Opening_bal_list.txtMajorGroup.value=majorDesc;
     document.Opening_bal_list.txtMinorGroup.value=minordesc;
     
     document.Opening_bal_list.txtSubGrp1.value=subdesc1;
     if(subdesc2==0)
     document.Opening_bal_list.txtSubGrp2.value="";
     else
     document.Opening_bal_list.txtSubGrp2.value=subdesc2;
////////////////////////////
    
   var tbody=document.getElementById("tb");
                        var t=0;
                        
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
    
    
        var j=0;
        var AcId=baseResponse.getElementsByTagName("AcId");
        var AcName=baseResponse.getElementsByTagName("AcName");
        var uptoCR=baseResponse.getElementsByTagName("uptoCR");
        var uptoDB=baseResponse.getElementsByTagName("uptoDB");
        var currDR=baseResponse.getElementsByTagName("currDR");
        var currCR=baseResponse.getElementsByTagName("currCR");
        
       
        var len=AcId.length;
     for(j=0;j<len;j++)
     {
        var tbody=document.getElementById("tb");
         var AcId=baseResponse.getElementsByTagName("AcId");
        var AcName=baseResponse.getElementsByTagName("AcName");
        var uptoCR=baseResponse.getElementsByTagName("uptoCR");
        var uptoDB=baseResponse.getElementsByTagName("uptoDB");
        var currDR=baseResponse.getElementsByTagName("currDR");
        var currCR=baseResponse.getElementsByTagName("currCR");
        
       
      
        var AcId1=AcId.item(j).firstChild.nodeValue;
        var AcName1=AcName.item(j).firstChild.nodeValue;
        var uptoCR1=uptoCR.item(j).firstChild.nodeValue;
        var uptoDB1=uptoDB.item(j).firstChild.nodeValue;
        var currDR1=currDR.item(j).firstChild.nodeValue;
        var currCR1=currCR.item(j).firstChild.nodeValue;
        
         var items=new Array();
        
        
        items[0]=AcId1;
        items[1]=AcName1;
        items[2]=uptoCR1;
        items[3]=uptoDB1;
        items[4]=currDR1;
        items[5]=currCR1;
       
       
       
        var mycurrent_row=document.createElement("TR");
        
        mycurrent_row.id=items[0];
       
        var i=0;
        var cell2;
        
        for(i=0;i<6;i++)
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
     alert("Records not found");
        var tbody=document.getElementById("tb");
                        var t=0;
                        
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
        
       
    }
    
    
}

function listRangeRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    /////////////no textbox display here/////////////////
    
    document.Opening_bal_list.txtMajorGroup.value="";
     document.Opening_bal_list.txtMinorGroup.value="";
     document.Opening_bal_list.txtSubGrp1.value="";
     document.Opening_bal_list.txtSubGrp2.value="";
     ///////////////////////////////////
   var tbody=document.getElementById("tb");
                        var t=0;
                        
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
    
    if(flag=="success")
    {
        var tbody=document.getElementById("tb");
        var j=0;
        var AcId=baseResponse.getElementsByTagName("AcId");
        var AcName=baseResponse.getElementsByTagName("AcName");
        var uptoCR=baseResponse.getElementsByTagName("uptoCR");
        var uptoDB=baseResponse.getElementsByTagName("uptoDB");
        var currDR=baseResponse.getElementsByTagName("currDR");
        var currCR=baseResponse.getElementsByTagName("currCR");
        
       
        var len=AcId.length;
     for(j=0;j<len;j++)
     {
         var AcId=baseResponse.getElementsByTagName("AcId");
        var AcName=baseResponse.getElementsByTagName("AcName");
        var uptoCR=baseResponse.getElementsByTagName("uptoCR");
        var uptoDB=baseResponse.getElementsByTagName("uptoDB");
        var currDR=baseResponse.getElementsByTagName("currDR");
        var currCR=baseResponse.getElementsByTagName("currCR");
        
       
      
        var AcId1=AcId.item(j).firstChild.nodeValue;
        var AcName1=AcName.item(j).firstChild.nodeValue;
        var uptoCR1=uptoCR.item(j).firstChild.nodeValue;
        var uptoDB1=uptoDB.item(j).firstChild.nodeValue;
        var currDR1=currDR.item(j).firstChild.nodeValue;
        var currCR1=currCR.item(j).firstChild.nodeValue;
        
         var items=new Array();
        
        
        items[0]=AcId1;
        items[1]=AcName1;
        items[2]=uptoCR1;
        items[3]=uptoDB1;
        items[4]=currDR1;
        items[5]=currCR1;
       
       
       
        var mycurrent_row=document.createElement("TR");
        
        mycurrent_row.id=items[0];
       
        var i=0;
        var cell2;
        
        for(i=0;i<6;i++)
        {
            cell2=document.createElement("TD");
            var currentText=document.createTextNode(items[i]);
            cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);
        }
        
              
        tb.appendChild(mycurrent_row);
        }
       
    }
    else
    {
      
        //var tbody=document.getElementById("tb");
                        var t=0;
                        
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
        alert("Records not found");
       
    }
    
    
}

