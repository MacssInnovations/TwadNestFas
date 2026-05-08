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
    var lab=document.getElementById("labcode").value;
    lab=lab.split("--");
    var ctype=document.getElementById("ct").value;
    var url="../../../../../../wqs_invoice_details?command=load_customer&lcode="+lab[0]+"&type="+ctype;
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
              var command=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
              if(command=="load_customer")
              {
                  getDetails(baseResponse);
              }
            }
      }
}
function getDetails(baseresponse)
{
     var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=="success")
     {
            var count=baseresponse.getElementsByTagName("count");
            var tbody=document.getElementById("tb");
            for(var i=0;i<count.length;i++)
            {
                var cid=count[i].getElementsByTagName("cid")[0].firstChild.nodeValue;
                var name=count[i].getElementsByTagName("name")[0].firstChild.nodeValue;
                var address=count[i].getElementsByTagName("address")[0].firstChild.nodeValue;
                
                var mycurrent_row=document.createElement("TR");
                mycurrent_row.id=cid;
           
                var cell=document.createElement("TD");
                var check="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                    check=document.createElement("<INPUT type='radio' name='check1' value='"+cid+"' size='10'>");
                }
                else
                {  
                       check=document.createElement("input");
                       check.type="radio";
                       check.name="check1";
                       check.value=cid;
                }
                cell.appendChild(check);
                mycurrent_row.appendChild(cell);
                
                var cell2=document.createElement("TD");
                cell2.setAttribute('align','left');
                var pan2="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                        pan2=document.createElement("<INPUT type='hidden' name='cid' id='cid' size='3' style='background-color:#ececec' value='"+cid+"'>");
                }
                else
                {   
                        pan2=document.createElement("input");
                        pan2.type="hidden";
                        pan2.size="3";
                        pan2.name="cid";
                        pan2.id="cid";
                        pan2.value=cid;
                        pan2.readonly="readonly";
                }
                cell2.appendChild(pan2);
                var currentText1=document.createTextNode(cid);
                cell2.appendChild(currentText1);
                mycurrent_row.appendChild(cell2); 
           
                var cell2=document.createElement("TD");
                cell2.setAttribute('align','left');
                var pan2="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                        pan2=document.createElement("<INPUT type='hidden' name='name' id='name' size='3' style='background-color:#ececec' value='"+name+"'>");
                }
                else
                {   
                        pan2=document.createElement("input");
                        pan2.type="hidden";
                        pan2.size="3";
                        pan2.name="name";
                        pan2.id="name";
                        pan2.value=name;
                        pan2.readonly="readonly";
                }
                cell2.appendChild(pan2);
                var currentText1=document.createTextNode(name);
                cell2.appendChild(currentText1);
                mycurrent_row.appendChild(cell2); 
    
                var cell11=document.createElement("TD");
                cell11.setAttribute('align','left');
                var lpcd="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                        lpcd=document.createElement("<INPUT type='hidden' name='address' id='address' size='20' style='background-color:#ececec' value="+address+">");
                }
                else
                {   
                        lpcd=document.createElement("input");
                        lpcd.type="hidden";
                        lpcd.size="20";
                        lpcd.name="address";
                        lpcd.id="address";
                        lpcd.value=address;
                }
                cell11.appendChild(lpcd);
                var currentText5=document.createTextNode(address);
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