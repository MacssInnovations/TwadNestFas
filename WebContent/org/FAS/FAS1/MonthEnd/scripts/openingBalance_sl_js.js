
var seq=0;

function AjaxFunction() {
	
	
	var xmlrequest = false;
	try {
		//xmlrequest = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e1) {
		try {
			xmlrequest = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			xmlrequest = false;
		}
	}
	if (!xmlrequest && typeof XMLHttpRequest != 'undefined') {
		xmlrequest = new XMLHttpRequest();
	}
	return xmlrequest;
}



function doFunction(Command,param)
{
   if(Command=="checkCode")
        {
       
            var headcode=document.OpeningBalSubLedger.txtAcc_HeadCode.value;
         
            url="../../../../../SubLedgerMainFormServlet.con?Command=HeadCode&txtAcc_Head_code="+headcode;
            
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               headcodeResponse(req);
            }   
                    req.send(null);
        }
         if(Command=="Load_MasterSL_Code")
        {  
            var cmbSL_type=document.getElementById("cmbMas_SL_type").value;             // input from MASTER combo *
            cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            cmbOffice_code=document.getElementById("cmbOffice_code").value;
            if(cmbSL_type==5)
              {
              document.getElementById("offlist_div_master").style.display='block';
              clear_Combo(document.getElementById("cmbMas_SL_Code"));
              alert("USE search ICON to select the office");
              return true;
              }
            else
              document.getElementById("offlist_div_master").style.display='none';
              
           if(cmbSL_type!="")                              // called only not equal to null and 5 is for office
            {
                cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;
                other_dept_off_alias_id=document.getElementById("cmbMas_SL_Code").value;
                var url="../../../../../SubsidiaryLedgerServlet.con?Command=Load_MasterSL_Code&cmbSL_type="+cmbSL_type+"&cmbMas_SL_type="+cmbMas_SL_type+"&other_dept_off_alias_id="+other_dept_off_alias_id+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   
                   handleResponse(req);
                }   
                        req.send(null);
            }
            else if(cmbSL_type=="")
               clear_Combo(document.getElementById("cmbMas_SL_Code")); 
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
            if(Command=="Load_MasterSL_Code")
            {
                Load_MasterSL_Code(baseResponse);
            }
      }
    }
}


function Load_MasterSL_Code(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
var cmbSL_type=document.getElementById("cmbMas_SL_type").value;

    if(flag=="success")
    {
         var cmbSL_Code=document.getElementById("cmbMas_SL_Code");      // value assigned to same local variable name
         
         var items_id=new Array();
         var items_name=new Array();
         
            var cid=baseResponse.getElementsByTagName("cid");
            var cname=baseResponse.getElementsByTagName("cname");
            for(var k=0;k<cid.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
                items_name[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
            }
           
           clear_Combo(cmbSL_Code);
            
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_name[k];
                  option.value=items_id[k];
                   try
                  {
                      cmbSL_Code.add(option);
                  }
                  catch(errorObject)
                  {
                      cmbSL_Code.add(option,null);
                  }
            }
    }
    else if(flag=="failure")
    {
        alert("No data found");
        var cmbSL_Code=document.getElementById("cmbMas_SL_Code");   
        clear_Combo(cmbSL_Code);
    }
}

function headcodeResponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        { 
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
            {
                var headname=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
              //  document.OpeningBalSubLedger.txtAcc_HeadCode.value="";
                document.OpeningBalSubLedger.txtAcc_HeadDesc.value=headname;
                
               
                
                var BalType=baseResponse.getElementsByTagName("BalType")[0].firstChild.nodeValue;
                var SL_YN =baseResponse.getElementsByTagName("SL_YN")[0].firstChild.nodeValue;
                
                var cmbSL_type=document.getElementById("cmbMas_SL_type");   
                
       if(SL_YN=="Y")
       {
        
        var items_SLcode=new Array();
        var items_SLdesc=new Array();
            var SLCODE=baseResponse.getElementsByTagName("SLCODE");
            var SLDESC=baseResponse.getElementsByTagName("SLDESC");
            for(var k=0;k<SLCODE.length;k++)
            {
                items_SLcode[k]=baseResponse.getElementsByTagName("SLCODE")[k].firstChild.nodeValue;
                items_SLdesc[k]=baseResponse.getElementsByTagName("SLDESC")[k].firstChild.nodeValue;
            }
            
            cmbSL_type.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select SubLedger Type--";
            option.value="0";
            try
            {
                cmbSL_type.add(option);
            }catch(errorObject)
            {
                cmbSL_type.add(option,null);
            }
            for(var k=0;k<SLCODE.length;k++)
            {   
              var option=document.createElement("OPTION");
              option.text=items_SLdesc[k];
              option.value=items_SLcode[k];
               try
              {
                  cmbSL_type.add(option);
              }
              catch(errorObject)
              {
                  cmbSL_type.add(option,null);
              }
            }
       }
        if(SL_YN=="N" || SL_YN=="null")
           {    
                cmbSL_type.innerHTML=""; 
                var option=document.createElement("OPTION");
                option.text="--Select SubLedger Type--";
                option.value="0";
                try
                {
                    cmbSL_type.add(option);
                }catch(errorObject)
                {
                    cmbSL_type.add(option,null);
                }
            }
                
                
                
                //End Change on Date 05-jan-2007//
            }
            else
            {
                var cmbSL_type=document.getElementById("cmbMas_SL_type"); 
                document.OpeningBalSubLedger.txtAcc_HeadCode.value="";
                document.OpeningBalSubLedger.txtaccountheadname.value="";
                alert("Invalid HeadCode");
                document.OpeningBalSubLedger.txtAcc_HeadCode.focus();
                
                cmbSL_type.innerHTML=""; 
                var option=document.createElement("OPTION");
                option.text="--Select SubLedger Type--";
                option.value="0";
                try
                {
                    cmbSL_type.add(option);
                }catch(errorObject)
                {
                    cmbSL_type.add(option,null);
                }
            }
        }
        
    }

}

function list()
{
	
        var unit_id=document.getElementById("cmbAcc_UnitCode").value;
        var office_id=document.getElementById("cmbOffice_code").value;
        winemp= window.open("OpeningBalanceList_sl.jsp?unit_id="+unit_id+"&office_id="+office_id,"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
        winemp.moveTo(250,250);  
        winemp.focus();
}

function delete1()
{
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var txtFinanYr=document.getElementById("txtFinanYr").value;
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	//alert(txtCB_Year);
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	//alert(txtCB_Month);
	var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
	var r = confirm("Are U Sure?");
	if (r == true) {
		
		var url="../../../../../openingBalance_sl_servlet?command=deleted&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Month="+txtCB_Month+"&txtAcc_HeadCode="+txtAcc_HeadCode+
		"&txtCB_Year="+txtCB_Year;
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function add()
{

	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var txtFinanYr=document.getElementById("txtFinanYr").value;
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	
	var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
	var txtDebit=document.getElementById("txtDebit").value;
	var txtCredit=document.getElementById("txtCredit").value;
	var txtDrLUpdate=document.getElementById("txtDrLUpdate").value;
	var txtCrLUpdate=document.getElementById("txtCrLUpdate").value;
	var cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;
	var cmbMas_SL_Code=document.getElementById("cmbMas_SL_Code").value;
	        
        
	 var valid=txt_empty('txtAcc_HeadCode|txtDebit|txtCredit|txtDrLUpdate|txtCrLUpdate');
				
        if(valid)
        {
        
                var url="../../../../../openingBalance_sl_servlet?command=add&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtFinanYr="+txtFinanYr+"&txtCB_Month="+txtCB_Month+"&txtAcc_HeadCode="+txtAcc_HeadCode+
                "&txtDebit="+txtDebit+"&txtCredit="+txtCredit+"&txtDrLUpdate="+txtDrLUpdate+"&txtCrLUpdate="+txtCrLUpdate+"&txtCB_Year="+txtCB_Year+"&cmbMas_SL_type="+cmbMas_SL_type+"&cmbMas_SL_Code="+cmbMas_SL_Code;
              
                var xmlrequest=AjaxFunction();
                xmlrequest.open("POST",url,true);
                xmlrequest.onreadystatechange=function(){
                        
                        manipulate(xmlrequest);
                }
                xmlrequest.send(null);
	}
}

function update()
{
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var txtFinanYr=document.getElementById("txtFinanYr").value;
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	//alert(txtCB_Year);
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	//alert(txtCB_Month);
	var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
	var txtDebit=document.getElementById("txtDebit").value;
	var txtCredit=document.getElementById("txtCredit").value;
	var txtDrLUpdate=document.getElementById("txtDrLUpdate").value;
	var txtCrLUpdate=document.getElementById("txtCrLUpdate").value;
	var cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;
	var cmbMas_SL_Code=document.getElementById("cmbMas_SL_Code").value;
        
	 var valid=txt_empty('txtAcc_HeadCode|txtDebit|txtCredit|txtDrLUpdate|txtCrLUpdate');
				
				if(valid)
				{
	
	var url="../../../../../openingBalance_sl_servlet?command=update&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtFinanYr="+txtFinanYr+"&txtCB_Month="+txtCB_Month+"&txtAcc_HeadCode="+txtAcc_HeadCode+
	"&txtDebit="+txtDebit+"&txtCredit="+txtCredit+"&txtDrLUpdate="+txtDrLUpdate+"&txtCrLUpdate="+txtCrLUpdate+"&txtCB_Year="+txtCB_Year+"&cmbMas_SL_type="+cmbMas_SL_type+"&cmbMas_SL_Code="+cmbMas_SL_Code;
	var xmlrequest=AjaxFunction();
	xmlrequest.open("POST",url,true);
	xmlrequest.onreadystatechange=function(){
		
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
				}
	
}
function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
			
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var command = tagCommand.firstChild.nodeValue;
		

			if (command == "add") {
				addRow(baseResponse);
			} else if (command == "deleted") {
				deleteRow(baseResponse);
			} else if (command == "update") {
				updateRow(baseResponse);
			}
			else if(command=="verify")
			{
				VerifyResult(baseResponse);
			}

	}

}
}
function VerifyResult(baseResponse)
{
	
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		//alert("Record Inserted Into Database successfully.");
		//refresh();
	}
	else if(flag=="exists")
	{
		alert("Already Exists...... Check Account_Head_Code");
		
		refresh();
		document.getElementById("txtAcc_HeadCode").focus();
	}
	
	
	
	
}
function checkExists()
{
	//alert("enter");
	var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	//alert(txtCB_Year);
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	//alert(txtCB_Month);
	if(txtAcc_HeadCode=='')
	{
		alert("AccountHeadCode Should Not Be Blank");
		document.getElementById("txtAcc_HeadCode").focus();
		return false;
		
	}
	

var url="../../../../../openingBalance_sl_servlet?command=verify&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Month="+txtCB_Month+"&txtAcc_HeadCode="+txtAcc_HeadCode+"&txtCB_Year="+txtCB_Year;
//alert(url);
var xmlrequest=AjaxFunction();
xmlrequest.open("POST",url,true);
xmlrequest.onreadystatechange=function(){

manipulate(xmlrequest);
}
xmlrequest.send(null);
		}
	

function ParentDrawing(code,debit,creadit,ddate,cdate,sTypevalue,stype,scode,svalue)
{

	var acc=code.split("-");
	//alert(acc);
	document.getElementById("txtAcc_HeadCode").value=acc[0];
	document.getElementById("txtAcc_HeadDesc").value=acc[1];
	document.getElementById("txtDebit").value=debit;
	document.getElementById("txtCredit").value=creadit;
	document.getElementById("txtDrLUpdate").value=ddate;
     document.getElementById("txtCrLUpdate").value=cdate;
     
     document.getElementById("cmbMas_SL_type").value=stype;
   //  newMethod(stype);
     
     document.getElementById("cmbMas_SL_type").options[document.getElementById("cmbMas_SL_type").selectedIndex].text=sTypevalue;
     document.getElementById("cmbMas_SL_type").options[document.getElementById("cmbMas_SL_type").selectedIndex].value=stype;
     
     document.getElementById("cmbMas_SL_Code").options[document.getElementById("cmbMas_SL_Code").selectedIndex].text=svalue;
     document.getElementById("cmbMas_SL_Code").options[document.getElementById("cmbMas_SL_Code").selectedIndex].value=scode;
    // setTimeout('fun1('+scode+')',500);
    
     
	document.OpeningBalSubLedger.onsubmit.disabled = true;
	document.OpeningBalSubLedger.ondelete.disabled = false;
       document.OpeningBalSubLedger.onupdate.disabled = false;
	

	
}

//function newMethod(stype)
//{
//
//
//         var billcombo = document.OpeningBalSubLedger.cmbMas_SL_type;
//        document.OpeningBalSubLedger.cmbMas_SL_type.length=0;
//                 var opt = document.createElement('option');
//                 opt.value = stype;
//                 if(opt.value==1){
//                 opt.innerHTML = "Supplier"; 
//                 }
//                 if(opt.value==2){
//                 opt.innerHTML = "Firms"; 
//                 }
//                 if(opt.value==3){
//                 opt.innerHTML = "Assets"; 
//                 }
//                 if(opt.value==4){
//                 opt.innerHTML = "Cheque Books"; 
//                 }
//                 if(opt.value==5){
//                 opt.innerHTML = "Offices"; 
//                 }
//                 if(opt.value==6){
//                 opt.innerHTML = "Bank"; 
//                 }
//                 if(opt.value==7){
//                 opt.innerHTML = "Employees"; 
//                 }
//                
//                 if(opt.value==9){
//                 opt.innerHTML = "Other Departments"; 
//                 }
//                 if(opt.value==10){
//                 opt.innerHTML = "Project"; 
//                 }
//                 if(opt.value==11){
//                 opt.innerHTML = "Contractors"; 
//                 }
//                 if(opt.value==12){
//                 opt.innerHTML = "Scheme Owner"; 
//                 }
//                 if(opt.value==13){
//                 opt.innerHTML = "Beneficiary"; 
//                 }
//                 if(opt.value==14){
//                 opt.innerHTML = "DCB Beneficiary"; 
//                 }
//                 if(opt.value==15){
//                 opt.innerHTML = "Accounting Units"; 
//                 }
//                 if(opt.value==16){
//                 opt.innerHTML = "Scheme Type"; 
//                 }
//                 billcombo.appendChild(opt);
//            
//
//}

function updateRow(baseResponse) {
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
	alert("Update successfully");
	refresh();
	}
	
	else {
		alert("Failed to update values");
	}
}
function deleteRow(baseResponse) {
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
	alert("Delete successfully");
	refresh();
	}
}
function addRow(baseResponse) {
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Inserted Into Database successfully.");
		refresh();
	}
	else if(flag=="exists")
	{
		alert("Already Exists...... Check PrimaryKey");
		refresh();
	}
	else
	{
		alert("Fail To Add");
		refresh();
	}
}
function refresh()
{
    document.getElementById("txtDrLUpdate").value='';
    document.getElementById("txtCrLUpdate").value='';
    document.getElementById("txtDebit").value='';
    document.getElementById("txtCredit").value='';	
    document.getElementById("txtAcc_HeadCode").value='';
    document.getElementById("txtAcc_HeadDesc").value='';
   // document.getElementById("cmbMas_SL_type").value='';
  //  document.getElementById("cmbMas_SL_Code").value='';
    
}
function exitfun() {
	alert("Exit ");
	window.close();
}

function txt_empty(txt) 
{
	var k=0;
	var s=txt.split('|');

	for(var i=0;i<s.length;i++)
	{
	if(document.getElementById(s[i]).value == "") 
	{ 
		
	var a=s[i].split('txt');	
	alert(a[1]+"   Should Not Be Blank");
	document.getElementById(s[i]).focus();
	return false;
	} 
	
	
	}
    return true;
}
function clear_Combo(combo)
{
        //alert(combo.id)
        var cmbSL_Code=document.getElementById(combo.id);   
        cmbSL_Code.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select SubLedger Code--";
        option.value="";
        try
        {
            cmbSL_Code.add(option);
        }catch(errorObject)
        {
            cmbSL_Code.add(option,null);
        }
}

