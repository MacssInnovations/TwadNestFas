
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

function disp()
{
        var req=getTransport();
        var txtCB_Year=document.ReportFreeze.txtCB_Year.value;
        var txtCB_Month=document.ReportFreeze.txtCB_Month.value;
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        var url="../../../../../Accept_Report_Freeze?command=check&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;                
        var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse1(req);
            }   
                    req.send(null);
}

function nullcheck()
{
    if(document.getElementById("txtCB_Year").value=="")
       {
        alert("Enter Year");
        document.getElementById("txtCB_Year").focus();
        return false;
       }
      else if(document.getElementById("txtAdvice_No").value=="")
        {
            alert("Enter Advice No");
            document.getElementById("txtAdvice_No").focus();
            return false;
        }
      else
            return true;
}

function update()
{
        var txtCB_Year=document.ReportFreeze.txtCB_Year.value;
        var txtCB_Month=document.ReportFreeze.txtCB_Month.value;
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        var txtAdvice_No=(document.getElementById("txtAdvice_No").value).split("-");
        //alert(txtAdvice_No[0]);
       // var spladv=txtAdvice_No.split("-");
      //  var	vou_no=
        if(nullcheck())
        {
        var url="../../../../../Accept_Report_Freeze?command=updated&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&vou_no="+txtAdvice_No[0]+"&slno="+txtAdvice_No[1];                
        var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse1(req);
            }   
                  req.send(null);
        }  
       
        	
}

function handleResponse1(req)
{ 
	//alert("siva");
    if(req.readyState==4)
    {
    	//alert(req.status);
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];//alert(baseResponse);
            
            var tagcommand=baseResponse.getElementsByTagName("command")[0];//alert(tagcommand);
            
            var Command=tagcommand.firstChild.nodeValue;//alert(Command);
            
             if(Command=="check")
            {
                DispRow(baseResponse);
            }
             else if(Command=="updated")
             {
            	 UpdateRow(baseResponse);
             }
           }
         }
}

function UpdateRow(baseResponse)
{

    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        alert("Record Updated");
        document.ReportFreeze.txtCB_Year.value="";
        var advopt=document.getElementById("txtAdvice_No");
        advopt.options[advopt.selectedIndex].value="";
        advopt.options[advopt.selectedIndex].text="select Advice No";
    }
    else
    {
        alert("Record not Updated");
    }
}

function DispRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        minorType=document.getElementById("txtAdvice_No");
        minorType.length=0;
        mtype=baseResponse.getElementsByTagName("memono");    
        for(var i=0;i<mtype.length;i++)
               {
                var items_id=baseResponse.getElementsByTagName("memono")[i].firstChild.nodeValue;
                var slno_id=baseResponse.getElementsByTagName("slno")[i].firstChild.nodeValue;
                var option=document.createElement("OPTION");
                option.text=items_id+"-"+slno_id;
                option.value=items_id+"-"+slno_id;
                try
                {
                        minorType.add(option);
                }
                catch(errorObject)
                {
                        minorType.add(option,null);
                }
        }
    }
    else
       {
               alert("No records");
               document.getElementById("txtAdvice_No").value="";
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
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
            {
                return false;
            }
        }
}

