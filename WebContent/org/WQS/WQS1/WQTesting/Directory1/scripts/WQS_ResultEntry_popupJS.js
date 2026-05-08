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
    var cmd=document.getElementById("command").value;
    if(cmd=="create")
    {
        var url="../../../../../../WQS_ResultEntry_Serv?command=load_invoice&lab="+lab[0];
    }
    else if(cmd=="edit")
    {
        var url="../../../../../../WQS_ResultEntry_EditServ?command=load_invoice&lab="+lab[0];
    }
    else if(cmd=="validate")
    {
        var url="../../../../../../WQS_ResultEntry_ValidateServ?command=load_invoice&lab="+lab[0];
    }
    else if(cmd=="Revalidation")
    {
        var url="../../../../../../WQS_ResultEntry_ReValidateServ?command=load_invoice&lab="+lab[0];
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
        var cmd=document.getElementById("command").value;
        if(cmd=="create")
            var tbody=document.getElementById("tb");
        else if(cmd=="edit")
            var tbody=document.getElementById("tb_edit");
        else
            var tbody=document.getElementById("tb_det");
        var ino=new Array();
        var cdate=new Array();
        var test_purpose=new Array();
        var sno=new Array();
        var ctype=new Array();
        try
        {
            tbody.innerHTML="";
        }
        catch(e)
        {
            tbody.innerText="";
        }
        var sn=0;
        for(var i=0;i<count.length;i++)
        {
             sn=i+1;
             ino[i]=count[i].getElementsByTagName("ino")[0].firstChild.nodeValue;             
             if(cmd=="create" || cmd=="edit")
             {
                sno[i]=count[i].getElementsByTagName("sno")[0].firstChild.nodeValue;
                if(cmd!="edit")
                    cdate[i]=count[i].getElementsByTagName("cdate")[0].firstChild.nodeValue;
            }
             else
                ctype[i]=count[i].getElementsByTagName("ctype")[0].firstChild.nodeValue;
             test_purpose[i]=count[i].getElementsByTagName("test_purpose")[0].firstChild.nodeValue;
       
            var mycurrent_row=document.createElement("TR");
            mycurrent_row.id=sn;
       
            var cell=document.createElement("TD");
            var check="";
            if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
            {
                check=document.createElement("<INPUT type='radio' name='check1' value='"+sn+"' size='10'>");
            }
            else
            {  
                   check=document.createElement("input");
                   check.type="radio";
                   check.name="check1";
                   check.value=sn;
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
            if(cmd=="create" || cmd=="edit")
            {
                    var cell10=document.createElement("TD");
                    cell10.setAttribute('align','left');
                    var lpcd="";
                    if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                    {
                            lpcd=document.createElement("<INPUT type='hidden' name='sno' id='sno' size='20' style='background-color:#ececec' value="+sno[i]+">");
                    }
                    else
                    {   
                            lpcd=document.createElement("input");
                            lpcd.type="hidden";
                            lpcd.size="20";
                            lpcd.name="sno";
                            lpcd.id="sno";
                            lpcd.value=sno[i];
                    }
                    cell10.appendChild(lpcd);
                    var currentText3=document.createTextNode(sno[i]);
                    cell10.appendChild(currentText3);
                    mycurrent_row.appendChild(cell10); 
                    if(cmd!="edit")
                    {
                            var cell12=document.createElement("TD");
                            cell12.setAttribute('align','left');
                            var lpcd="";
                            if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                            {
                                    lpcd=document.createElement("<INPUT type='hidden' name='cdate' id='cdate' size='20' style='background-color:#ececec' value="+cdate[i]+">");
                            }
                            else
                            {   
                                    lpcd=document.createElement("input");
                                    lpcd.type="hidden";
                                    lpcd.size="20";
                                    lpcd.name="cdate";
                                    lpcd.id="cdate";
                                    lpcd.value=cdate[i];
                            }
                            cell12.appendChild(lpcd);
                            var currentText5=document.createTextNode(cdate[i]);
                            cell12.appendChild(currentText5);
                            mycurrent_row.appendChild(cell12); 
                    }
            }
            else
            {
                    var cell10=document.createElement("TD");
                    cell10.setAttribute('align','left');
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
                    cell10.appendChild(lpcd);
                    var currentText3=document.createTextNode(ctype[i]);
                    cell10.appendChild(currentText3);
                    mycurrent_row.appendChild(cell10); 
            }
            var cell11=document.createElement("TD");
            cell11.setAttribute('align','left');
            var lpcd="";
            if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
            {
                    lpcd=document.createElement("<INPUT type='hidden' name='test_purpose' id='test_purpose' size='20' style='background-color:#ececec' value="+test_purpose[i]+">");
            }
            else
            {   
                    lpcd=document.createElement("input");
                    lpcd.type="hidden";
                    lpcd.size="20";
                    lpcd.name="test_purpose";
                    lpcd.id="test_purpose";
                    lpcd.value=test_purpose[i];
            }
            cell11.appendChild(lpcd);
            var currentText5=document.createTextNode(test_purpose[i]);
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
                    var cid=v[i].value;
                    var r=document.getElementById(cid);
                    var rcells=r.cells;
                    record1=rcells.item(1).firstChild.value;
                    record2=rcells.item(2).firstChild.value;
                    Minimize();
                    opener.doParentEmp(record1,record2);
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