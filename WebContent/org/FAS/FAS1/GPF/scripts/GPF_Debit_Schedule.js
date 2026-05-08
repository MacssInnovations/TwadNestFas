//alert("Welcome JS is called!...");

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


function callminor()
{
        var req=getTransport();
        var majorType=document.GPF_Debit.majorType.value;
        var url="../../../../../GPF_Debit_Schedule?Command=check&majorType="+majorType;   
        alert("URL=1=>"+url);
        var req=getTransport();
            req.open("POST",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
}


function  callsub(param)
{
	    var major1=document.GPF_Debit.majorType.value;
        var url="../../../../../GPF_Debit_Schedule?Command=subType&sub2="+param+"&major2="+major1;
        alert("URL=2=>"+url);
        var req=getTransport();
        req.open("POST",url,true); 
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
            
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            
            var Command=tagcommand.firstChild.nodeValue;  
            if(Command=="Disp")
            {            
                DispRow(baseResponse);
            }
            else if(Command=="subb")
            {
                subtypechecking(baseResponse);
            }
        }
    }
}

function subtypechecking(baseResponse)
{

          var subcmb = document.forms[0].billsubtype;
          document.forms[0].billsubtype.length=0;
          var subcode = baseResponse.getElementsByTagName("subcode"); 
          var subdesc = baseResponse.getElementsByTagName("subdesc"); 
          for(var i=0; i<subcode.length; i++)
               {
        	         var opt1 = document.createElement('option');
                     opt1.value = subcode[i].firstChild.nodeValue;
                     opt1.innerHTML = subdesc[i].firstChild.nodeValue; 
                     subcmb.appendChild(opt1);
               }  
}




function DispRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
//        minorType=document.getElementById("minorType");
//        minorType.length=0;
        mtype=baseResponse.getElementsByTagName("mindesc");  
        var len =baseResponse.getElementsByTagName("mincode").length;
	   	 if(len>0)
	   	 {
	   		var minorType = document.getElementById("minorType");
	   		minorType.length=1;
        for(var i=0;i<mtype.length;i++)
               {
                var items_id=baseResponse.getElementsByTagName("mincode")[i].firstChild.nodeValue;
                var items_name=baseResponse.getElementsByTagName("mindesc")[i].firstChild.nodeValue;				       	                                                  
                var option=document.createElement("OPTION");
                option.text=items_name;
                option.value=items_id;
                try
                {
                        minorType.add(option);
                }
                catch(errorObject)
                {
                        minorType.add(option,null);
                }}
        }
    }
    else
       {
               alert("No records");
               document.GPF_Debit.majorType.selectedIndex="";
        }
}
function SubmitFun()
{
	var flag=checkNull();
	//alert("Flag===>"+flag);
	if(flag)
	{
	
	var unit_id=document.getElementById("cmbAcc_UnitCode").value;
	var office_id=document.getElementById("cmbOffice_code").value;
	var cbyear=document.getElementById("cbyear").value;
	var cbmonth=document.getElementById("cbmonth").value;
	var majorType=document.getElementById("majorType").value;
    var minorType=document.getElementById("minorType").value;
    var subtype=document.getElementById("billsubtype").value;
    var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
    alert(txtEmpID_mas);
    var cmbMas_SL_Code1 = document.getElementById("cmbMas_SL_Code");
    var cmbMas_SL_Code = cmbMas_SL_Code1.options[cmbMas_SL_Code1.selectedIndex].text;
    alert(cmbMas_SL_Code);
    
	var type=document.getElementById("debit_type").value;
	var url="../../../../../GPF_Debit_Schedule?Command=Add&unit_id="+unit_id+"&office_id="+office_id+"&cbyear="+cbyear+"&cbmonth="+cbmonth+"&type="+type+"&majorType="+majorType+"&minorType="+minorType+"&subtype="+subtype+
	"&txtEmpID_mas="+txtEmpID_mas+"&cmbMas_SL_Code="+cmbMas_SL_Code;
	//alert("URL===>"+url);
	var req = getTransport();

	req.open("POST", url, true);

	req.onreadystatechange = function() {
		manipulate(req);
	}
	req.send(null);
	}
	else
		{
		return false;
		}
	
}
function callemp(path)
{
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	
	
	
		var url = path+ "/Bills_Token_Register_with_SP?command=getempname_off&txtEmpID_mas="+ txtEmpID_mas+"&cmbOffice_code="+cmbOffice_code+"&cmbMas_SL_Code="+cmbMas_SL_Code;
		// alert(url);
		var req = getTransport();
		req.open("POST", url, true);
		req.onreadystatechange = function() {
			manipulate1(req);
		};

		req.send(null);

}

function manipulate1(req) {

	if (req.readyState == 4) {
		if (req.status == 200) {

			var baseResponse = req.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			
			 if (command == "getempname_off") {
				 //alert("manipulate");
				getempname_re(baseResponse); }
			
			}
	}
}
function getempname_re(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
	var empname = baseResponse.getElementsByTagName("empname")[0].firstChild.nodeValue;
	
	document.getElementById("cmbMas_SL_Code").length = 0;
	var len1 = baseResponse.getElementsByTagName("empname").length;
	for ( var i = 0; i < len1; i++) {
		var empid = baseResponse.getElementsByTagName("empid")[i].firstChild.nodeValue;
		var empname = baseResponse.getElementsByTagName("empname")[i].firstChild.nodeValue;
		var se = document.getElementById("cmbMas_SL_Code");
		var op = document.createElement("OPTION");
		op.value = empid;
		var txt = document.createTextNode(empname);
		op.appendChild(txt);
		se.appendChild(op);
	
	}
	}
	else
	{
		alert("Enter Relevant EmployeeId For This Office");
		document.getElementById("cmbMas_SL_Code").length = 0;
		document.getElementById("txtEmpID_mas").value="";
	}
}

function manipulate(req) {

	if (req.readyState == 4) {
//		alert("readyState===>"+req.readyState);
		if (req.status == 200) 
//			alert("status===>"+req.status);
		{
			//alert("Response===>"+req.responseText);
			 var baseResponse=req.responseXML.getElementsByTagName("response")[0];
			 //alert("baseResponse:::"+baseResponse);
			 var tagcommand=baseResponse.getElementsByTagName("command")[0];
			 var Command=tagcommand.firstChild.nodeValue;
			 //alert("Command==>"+Command);
			if (Command == "Add") {
				add(baseResponse);
			}
		}
	}
}

function add(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;	
//	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;	
	if (flag == "success") {
		alert("GPF_Debit Head Inserted Successfully!.........");
	}
	
	else if (flag == "fail"){
		alert("GPF Debit Head not Found");
	}
	else if(flag == "TB_success")
		{
		
		}
	else if(flag == "TB_failure")
		{
		alert("Trial Balance is not Closed");
		return false;
		}
}


function checkNull()
{
// alert("Welcome"); 
if(document.getElementById("cmbAcc_UnitCode").value=="")
{
    alert("Select the Account Unit code");
    //document.getElementById("txtAcc_HeadDesc").focus();
    return false;    
}
if(document.getElementById("cmbOffice_code").value=="")
{
    alert("Select the Office Code");
    //document.getElementById("cmbOffice_code").focus();
    return false;
}
if(document.getElementById("cbyear").value=="")
{
    alert("Select the cashbook year");
    //document.getElementById("cmbOffice_code").focus();
    return false;
}
if(document.getElementById("cbmonth").value=="")
{
    alert("Select the cashbook month");
    //document.getElementById("cmbOffice_code").focus();
    return false;
}
if(document.getElementById("debit_type").value=="")
{
    alert("Select the debit_type");
    //document.getElementById("cmbOffice_code").focus();
    return false;
}
return true;

}



function clrForm()
{
	
	if(window.confirm("Do you want to clear ALL fields ?"))
	 {
	   call_clr();
	 }

}

function call_clr()
{
	
	document.getElementById("cbyear").value="";
	document.getElementById("cbmonth").value="";
	document.getElementById("debit_type").value="";

}
