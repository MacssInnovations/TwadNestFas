var req;
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
function loadMonthYear()
{
     var currentTime = new Date()
     var month=currentTime.getMonth();
     var year = currentTime.getFullYear()

     fin_year_from="",fin_year_to="";
     var yearCombo=document.getElementById("year");
     var child=yearCombo.childNodes;
     for(var i=child.length-1;i>1;i--)
     {
        yearCombo.removeChild(child[i]);
     }    
     i=0;
     if(document.level2_grouping.period[1].checked==true)
     {
             while(i<3)
             {
                var option=document.createElement("option");
                var text=document.createTextNode(year);
                option.setAttribute("value",year);
                option.appendChild(text);
                yearCombo.appendChild(option);
                year=year-1;i++;
            }
            document.getElementById("month").style.visibility="visible";
     }     else
     {
             if(month<4)
                year=year-1;
             while(i<3)
             {
                fin_year_from=year;fin_year_to=year+1;
                var option=document.createElement("option");
                var text=document.createTextNode(fin_year_from+"-"+fin_year_to);
                option.setAttribute("value",fin_year_from+"-"+fin_year_to);
                option.appendChild(text);
                yearCombo.appendChild(option);
                year=year-1;i++;
            }
            document.getElementById("month").style.visibility="hidden";
    }
}
function loadMajorHead()
{
    loadMonthYear();
    var req=getTransport();
    var url="../../../../../../MIS_Level3_AbstractReport_Serv?command=loadMajorHead";
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        process_response(req);
    }
    req.send(null);
}

function loadLevel2()
{
    var major_head=document.level2_grouping.major_head.value;
    var req=getTransport();
    var url="../../../../../../MIS_Level3_AbstractReport_Serv?command=loadLevel2&major_head="+major_head;
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        process_response(req);
    }
    req.send(null);
}
function loadLevel2()
{
    clearCombo1();
    var major_head=document.level2_grouping.major_head.value;
    var req=getTransport();
    var url="../../../../../../MIS_Level3_AbstractReport_Serv?command=loadLevel2&major_head="+major_head;
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        process_response(req);
    }
    req.send(null);
}

function loadLevel3()
{
    clearCombo2();
    var level2_id=document.level2_grouping.level2.value;
    var req=getTransport();
    var url="../../../../../../MIS_Level3_AbstractReport_Serv?command=loadLevel3&level2_id="+level2_id;
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        process_response(req);
    }
    req.send(null);
}

function process_response(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {
            var response=req.responseXML.getElementsByTagName("response")[0];
            var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            if(command=="loadMajorHead")
            {
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="Success")
                {
                     var count=response.getElementsByTagName("count");
                     var major_head=document.getElementById("major_head");
                     for(var i=0;i<count.length;i++)
                     {
                             var major_code=count[i].getElementsByTagName("major_head_code")[0].firstChild.nodeValue;
                             var major_desc=count[i].getElementsByTagName("major_head_desc")[0].firstChild.nodeValue;
                             var opt=document.createElement("option");
                             opt.setAttribute("value",major_code);
                             var opttext=document.createTextNode(major_desc);
                             opt.appendChild(opttext);
                             major_head.appendChild(opt);
                     }
                }
            }
            else if(command=="loadLevel2")
            {
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="Success")
                {
                     var count=response.getElementsByTagName("count");
                     var level2=document.getElementById("level2");
                     for(var i=0;i<count.length;i++)
                     {
                             var level2_code=count[i].getElementsByTagName("level2_head_code")[0].firstChild.nodeValue;
                             var level2_desc=count[i].getElementsByTagName("level2_head_desc")[0].firstChild.nodeValue;
                             var opt=document.createElement("option");
                             opt.setAttribute("value",level2_code);
                             var opttext=document.createTextNode(level2_desc);
                             opt.appendChild(opttext);
                             level2.appendChild(opt);
                     }
                }
            }
            else if(command=="loadLevel3")
            {
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="Success")
                {
                     var count=response.getElementsByTagName("count");
                     var level3=document.getElementById("level3");
                     for(var i=0;i<count.length;i++)
                     {
                             var level3_code=count[i].getElementsByTagName("level3_head_code")[0].firstChild.nodeValue;
                             var level3_desc=count[i].getElementsByTagName("level3_head_desc")[0].firstChild.nodeValue;
                             var opt=document.createElement("option");
                             opt.setAttribute("value",level3_code);
                             var opttext=document.createTextNode(level3_desc);
                             opt.appendChild(opttext);
                             level3.appendChild(opt);
                     }
                }
            }
        }
    }
}

function clearCombo1()
{
     var level2=document.getElementById("level2");
     var child=level2.childNodes;
     for(var i=child.length-1;i>1;i--)
     {
        level2.removeChild(child[i]);
     }    
     clearCombo2();
}
function clearCombo2()
{
     var level3=document.getElementById("level3");
     var child=level3.childNodes;
     for(var i=child.length-1;i>1;i--)
     {
        level3.removeChild(child[i]);
     }    
}

function checknull()
{
    if((document.getElementById("major_head").value=="")||(document.getElementById("major_head").selectedIndex==0))
    {
        alert("Select Major Head");
        document.getElementById("major_head").focus();
        return false;
    }
    else if((document.getElementById("level2").value=="")||(document.getElementById("level2").selectedIndex==0))
    {
        alert("Select Level2");
        document.getElementById("level2").focus();
        return false;
    }
    else if((document.getElementById("level3").value=="")||(document.getElementById("level3").selectedIndex==0))
    {
        alert("Select Level3");
        document.getElementById("level3").focus();
        return false;
    }
    else if((document.getElementById("heading").value=="")||(document.getElementById("heading").value.length==0))
    {
        alert("Enter Heading");
        document.getElementById("heading").focus();
        return false;
    }
    else 
    {
        if((document.getElementById("year").value=="")||(document.getElementById("year").selectedIndex==0))        
        {
            alert("Select year");
            document.getElementById("year").focus();
            return false;
        }
        else if((document.getElementById("month").value=="")||(document.getElementById("month").selectedIndex==0))        
        {
            if(document.level2_grouping.period[1].checked==true)
            {
                 alert("Select month");
                 document.getElementById("month").focus();
                 return false;
            }
            else
                return true
        }
        else
            return true;
    }
}