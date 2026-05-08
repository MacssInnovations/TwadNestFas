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
    var url="../../../../../../WQS_ResultEntry_ValidateServ?command=load_invoice&lab="+lab[0];
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
        var stype=new Array();
        var total_samples=new Array();
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
             stype[i]=count[i].getElementsByTagName("stype")[0].firstChild.nodeValue;
             ctype[i]=count[i].getElementsByTagName("ctype")[0].firstChild.nodeValue;
             total_samples[i]=count[i].getElementsByTagName("total_sample")[0].firstChild.nodeValue;
       
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
            var currentText1=document.createTextNode(ino[i]);
            cell2.appendChild(currentText1);
            mycurrent_row.appendChild(cell2); 

            var cell13=document.createElement("TD");
            cell13.setAttribute('align','left');
            var currentText5=document.createTextNode(stype[i]);
            cell13.appendChild(currentText5);
            mycurrent_row.appendChild(cell13); 
            
            var cell14=document.createElement("TD");
            cell14.setAttribute('align','left');
            var currentText5=document.createTextNode(ctype[i]);
            cell14.appendChild(currentText5);
            mycurrent_row.appendChild(cell14); 
            
            var cell15=document.createElement("TD");
            cell15.setAttribute('align','left');
            var currentText5=document.createTextNode(total_samples[i]);
            cell15.appendChild(currentText5);
            mycurrent_row.appendChild(cell15); 
            
            
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