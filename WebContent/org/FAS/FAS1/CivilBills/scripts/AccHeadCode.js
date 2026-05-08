var val;
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
var winAccHeadCode;
function AccHeadpopup()
{
    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) 
    {
       winAccHeadCode.resizeTo(500,500);
       winAccHeadCode.moveTo(250,250); 
       winAccHeadCode.focus();
    }
    else
    {
        winAccHeadCode=null
    }
        
    winAccHeadCode= window.open("../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Acc_Head_Dir_List_InUse.jsp","AccountHeadSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAccHeadCode.moveTo(250,250);  
    winAccHeadCode.focus();
    
}
function doParentAccHead(code)
{
   document.getElementById("txtAcc_HeadCode").value=code;
   doFunction('checkCode',true);
   return true;
}
function doFunction(Command,param)
{   
     if(Command=="checkCode")
        {  
            var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
            var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            if(txtAcc_HeadCode.length>=6)
            {
                var url="../../../../../Receipt_SL.view?Command=checkCode&txtAcc_HeadCode="+txtAcc_HeadCode;
                alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }         
        }
}
function handleResponse(req)
{  
    if(req.readyState==4)
    { 
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="checkCode")
            {
                loadcheckCode(baseResponse);
            }
        }
    }
}
function loadcheckCode(baseResponse)
{
        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="success")
        {
             var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
             document.getElementById("txtAcc_HeadCode").value=hid;
             var hdesc=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
             document.getElementById("txtAcc_HeadDesc").value=hdesc;
        }
          else if(flag=="failure")
         {
             alert("Account Head Code '"+document.getElementById("txtAcc_HeadCode").value+"' doesn't Exist");
             document.getElementById("txtAcc_HeadCode").value="";
             document.getElementById("txtAcc_HeadCode").focus();
         }
}
function sixdigit(val)
{

 if(val.length!=0)
    {
        if(val.length<6)
        {
        alert("Account Head Code shouldn't be less than 6 digit number");
        return false;
        }
    }
}
function numbersonly(e,t)
{
         var unicode=e.charCode? e.charCode : e.keyCode;
         if(unicode==13)
         {
	          try{t.blur(); }catch(e){}
	          return true;
        
         }
         if (unicode!=8 && unicode !=9)
         {
	          if (unicode<48||unicode>57 ) 
	          {
	                return false 
	          }
         }
}  