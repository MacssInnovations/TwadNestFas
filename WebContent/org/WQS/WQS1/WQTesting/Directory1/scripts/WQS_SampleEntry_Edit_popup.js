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
    if (!req && typeof XMLHttpRequest != 'undefined') 
    {
        req=new XMLHttpRequest();
    }
    return req;
   
}   
function loadfun()
{
    var req=getTransport();
    lab=document.getElementById("labcode").value;
    lab=lab.split("--");
    var command=document.getElementById("command").value;
    if(command=="SampleEdit")
    {
        var url="../../../../../../WQS_SampleEntry_EditServ?option=load_invoice&lab="+lab[0];
    }
    else if(command=="Revalidation")
    {
        var url="../../../../../../WQS_ResultEntry_ReValidateServ?command=load_invoice&lab="+lab[0];
    }
    else if(command=="LocationRevalidation")
    {
        var url="../../../../../../WQS_SampleEntry_ReValidateServ?command=load_invoice&lab="+lab[0];
    }
    req.open("GET",url,"true");
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);                                       
}
function manipulate(req)
{
    if(req.readyState==4)
    {
          if(req.status==200)
          {              
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue;
              if(command=="load_invoice")
              {
                  getDetails(baseResponse);
              }
            }
      }
}

function getDetails(baseResponse)
{
    flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        var count=baseResponse.getElementsByTagName("count");//.firstChild.nodeValue;
        var tbody=document.getElementById("tb");
        var ino=new Array();
        var ctype=new Array();
        var invoice_date=new Array();
         try
         {
            tbody.innerHTML="";
        }
        catch(e)
        {
            tbody.innerText="";
        }
       for(var i=0;i<count.length;i++)
       {
             ino[i]=count[i].getElementsByTagName("ino")[0].firstChild.nodeValue;
             invoice_date[i]=count[i].getElementsByTagName("invoice_date")[0].firstChild.nodeValue;
             ctype[i]=count[i].getElementsByTagName("ctype")[0].firstChild.nodeValue;
       
            var mycurrent_row=document.createElement("TR");
            mycurrent_row.id=ino[i];
       
            var cell=document.createElement("TD");
            var check="";
            if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
            {
                check=document.createElement("<INPUT type='radio' name='check1' value='"+ino[i]+"' size='10'>");
            }
            else
            {  
                   check=document.createElement("input");
                   check.type="radio";
                   check.name="check1";
                   check.value=ino[i];
            }
            cell.appendChild(check);
            mycurrent_row.appendChild(cell);
       
            var cell2=document.createElement("TD");
            cell2.setAttribute('align','left');
            var pan2="";
            if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
            {
                    pan2=document.createElement("<INPUT type='hidden' name='ino' id='ino' size='3' style='background-color:#ececec' value='"+ino[i]+"'>");
            }
            else
            {   
                    pan2=document.createElement("input");
                    pan2.type="hidden";
                    pan2.size="3";
                    pan2.name="ino";
                    pan2.id="ino";
                    pan2.value=ino[i];
                    pan2.readonly="readonly";
            }
            cell2.appendChild(pan2);
            var currentText1=document.createTextNode(ino[i]);
            cell2.appendChild(currentText1);
            mycurrent_row.appendChild(cell2); 

            var cell11=document.createElement("TD");
            cell11.setAttribute('align','left');
            var lpcd="";
            if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
            {
                    lpcd=document.createElement("<INPUT type='hidden' name='stype' id='stype' size='20' style='background-color:#ececec' value="+invoice_date[i]+">");
            }
            else
            {   
                    lpcd=document.createElement("input");
                    lpcd.type="hidden";
                    lpcd.size="20";
                    lpcd.name="stype";
                    lpcd.id="stype";
                    lpcd.value=invoice_date[i];
            }
            cell11.appendChild(lpcd);
            var currentText5=document.createTextNode(invoice_date[i]);
            cell11.appendChild(currentText5);
            mycurrent_row.appendChild(cell11); 
            
            var cell11=document.createElement("TD");
            cell11.setAttribute('align','left');
            var lpcd="";
            if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
            {
                    lpcd=document.createElement("<INPUT type='hidden' name='ctype' id='ctype' size='20' style='background-color:#ececec' value="+ctype[i]+">");
            }
            else
            {   
                    lpcd=document.createElement("input");
                    lpcd.type="hidden";
                    lpcd.size="20";
                    lpcd.name="ctype";
                    lpcd.id="ctype";
                    lpcd.value=ctype[i];
            }
            cell11.appendChild(lpcd);
            var currentText5=document.createTextNode(ctype[i]);
            cell11.appendChild(currentText5);
            mycurrent_row.appendChild(cell11); 
            
            
            tbody.appendChild(mycurrent_row);
    }
    }

}

function onSubmit()
{
    var v=document.getElementsByName("check1");
    if(v)
    {
        for(i=0;i<v.length;i++)
        {
                if(v[i].checked==true)
                {
                    Minimize();
                    opener.doParentEmp(v[i].value);
                    return true;
                }
        }
    }
}
function Minimize() 
{
    window.close();
    opener.window.focus();
}