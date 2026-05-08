/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////
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



/////////////////////////////////////////////   AccHeadpopup  /////////////////////////////////////////////////////
var winAccHeadCode;
var winListAllBudget;
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

//This is ListAll Budget Coding//////

function ListAllBudget()
{
    if (winListAllBudget && winListAllBudget.open && !winListAllBudget.closed) 
    {
       winListAllBudget.resizeTo(500,500);
       winListAllBudget.moveTo(250,250); 
       winListAllBudget.focus();
    }
    else
    {
        winListAllBudget=null;
    }
    
    if((document.frmBudget.cmbAcc_UnitCode.value=="")||(document.frmBudget.cmbAcc_UnitCode.value.length<=0)||(document.frmBudget.cmbAcc_UnitCode.value=="0"))
    {
        alert("Select Accounting Unit Id");
        document.frmBudget.cmbAcc_UnitCode.focus();
        return false;
    }
    
    else if((document.frmBudget.cmbOffice_code.value=="") || (document.frmBudget.cmbOffice_code.value.length<=0) || (document.frmBudget.cmbOffice_code.value=="0"))
    {
        alert("Select Office Code");
        document.frmBudget.cmbOffice_code.focus();
        return false;
    }      
    else if((document.frmBudget.cmbFinancialYear.value=="") || (document.frmBudget.cmbFinancialYear.value.length<=0) || (document.frmBudget.cmbFinancialYear.value=="0"))
    {
        alert("Select  Financial Year");
        document.frmBudget.cmbFinancialYear.focus();
        return false;
    }   
    winListAllBudget= window.open("../../../../../org/FAS/FAS1/Masters/jsps/ListAllBudget.jsp","ListAllBudget","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winListAllBudget.moveTo(250,250);  
    winListAllBudget.focus();
    
}

function doParentAccHead(code)
{
   document.frmBudget.txtaccountheadcode.value=code;
   doFunction('checkCode','null');
   return true;
}

window.onunload=function()
{
if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
if (winListAllBudget && winListAllBudget.open && !winListAllBudget.closed) winListAllBudget.close();
};

function blockHead()
{
	if(document.frmBudget.groupId[0].checked==true)
	{
		document.getElementById("head_div1").style.display="block";
		document.getElementById("head_div2").style.display="block";
		callHead();
	}
	else
	{
		document.getElementById("head_div1").style.display="none";
		document.getElementById("head_div2").style.display="none";
		
	}
}
function nullckeck(){
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;

	var cmbStatementName=document.getElementById("cmbStatementName").value;
	
	
	if(cmbAcc_UnitCode==0){
		alert("Select accounting unit id");
		return false;
	}else if(cmbOffice_code==0){
		alert("Select accounting office id");
		return false;
	}else if(cmbFinancialYear==0){
		alert("Select Financial Year");
		return false;
	}else if(cmbStatementName==0){
		alert("Select Statement Name");
		return false;
	}
	return true;
}

function checkFreeze(){
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;

	var cmbStatementName=document.getElementById("cmbStatementName").value;
	//var statementGp=document.getElementById("statementGp").value;
	
	if(nullckeck()){
		
	
	var url = "../../../../../BudgetMasterServlet.con?Command=checkFreeze"+
		"&cmbAccounting_Unit_Id="+cmbAcc_UnitCode+"&cmbOffice_Code="+cmbOffice_code+"" +
		"&cmbFinancial_Year="+cmbFinancialYear+
		"&cmbStatementName="+cmbStatementName;
	var xmlrequest = getTransport();
	//alert(url);
	xmlrequest.open("GET", url, true);
	
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);
	}
}
function checkRadio(){
	if((document.frmBudget.groupId[0].checked==false)&&(document.frmBudget.groupId[1].checked==false)){
		alert("Select Head Or Group");
		return false;
	}
	return true;
}
function getAmount(){
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;

	var cmbStatementName=document.getElementById("cmbStatementName").value;
	var statementGp=document.getElementById("statementGp").value;
	var typallocation;
	
	if(checkRadio()){
	if(document.frmBudget.groupId[0].checked==true){
		typallocation=document.frmBudget.groupId[0].value;
	}else {
		typallocation=document.frmBudget.groupId[1].value;
	}
	}
	if(statementGp==0){
		alert("Select statement group");
		return false;
	}
	
	if(nullckeck()){

		
		if(typallocation=='G'){
			var url = "../../../../../BudgetMasterServlet.con?Command=getAmount"+
			"&cmbAccounting_Unit_Id="+cmbAcc_UnitCode+"&cmbOffice_Code="+cmbOffice_code+"" +
			"&cmbFinancial_Year="+cmbFinancialYear+
			"&cmbStatementName="+cmbStatementName+"&statementGp="+statementGp+"&typallocation="+typallocation;
		}else if(typallocation=='H') {
			var txtaccountheadcode=document.getElementById("txtaccountheadcode").value;
			if(txtaccountheadcode==""){
				alert("Enter Account Headcode");
				document.getElementById("txtaccountheadcode").focus();
				return false;
			}else{
			var url = "../../../../../BudgetMasterServlet.con?Command=getAmount"+
			"&cmbAccounting_Unit_Id="+cmbAcc_UnitCode+"&cmbOffice_Code="+cmbOffice_code+"" +
			"&cmbFinancial_Year="+cmbFinancialYear+
			"&cmbStatementName="+cmbStatementName+"&statementGp="+statementGp+"&typallocation="+typallocation+"&txtAcc_Head_code="+txtaccountheadcode;
			}
		}
	

	var xmlrequest = getTransport();
	//alert(url);
	xmlrequest.open("GET", url, true);
	
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);
	}
	
}

function reallocation_fn()
{
	
	var cmbStatementName=document.getElementById("cmbStatementName").value;
	var statementGp=document.getElementById("statementGp").value;
	var head_code=document.getElementById("txtaccountheadcode").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	
	
	var url ="../../../../../BudgetMasterServlet.con?Command=head_test" +
	"&cmbOffice_Code="+cmbOffice_code+"" +
	"&cmbFinancial_Year="+cmbFinancialYear+
	"&cmbStatementName="+cmbStatementName+"&statementGp="+statementGp+"" +
	"&txtAcc_Head_code="+head_code;
	//alert(url);
	var xmlrequest = getTransport();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
	manipulate1(xmlrequest);
	};
	xmlrequest.send(null);	
} 
function getStatementName() {
		

	var url = "../../../../../BudgetMasterServlet.con?Command=getStatementName";
	var xmlrequest = getTransport();
	//alert(url);
	xmlrequest.open("GET", url, true);
	
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);
}
function chooseGroup()
{

	var statement=document.getElementById("cmbStatementName").value;
	if(statement==""){
		alert("Select StatementName");
		
	}else{
	var url ="../../../../../BudgetMasterServlet.con?Command=groupch&statement="+statement;
	//alert(url);
	var xmlrequest = getTransport();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);	
	}
}


function callStatement()
{

	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;

	var cmbStatementName=document.getElementById("cmbStatementName").value;
	var statementGp=document.getElementById("statementGp").value;
		
		var url ="../../../../../BudgetMasterServlet.con?Command=callstatement" +
				"&cmbAccounting_Unit_Id="+cmbAcc_UnitCode+"&cmbOffice_Code="+cmbOffice_code+"" +
				"&cmbFinancial_Year="+cmbFinancialYear+"" +
				"&cmbStatementName="+cmbStatementName+"&statementGp="+statementGp;
		//alert(url);
		var xmlrequest = getTransport();
		xmlrequest.open("GET", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate1(xmlrequest);
		};
		xmlrequest.send(null);
} 
function callHead()
{
	
var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
var cmbOffice_code=document.getElementById("cmbOffice_code").value;
var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;

var cmbStatementName=document.getElementById("cmbStatementName").value;
var statementGp=document.getElementById("statementGp").value;
	
	var url ="../../../../../BudgetMasterServlet.con?Command=callHead" +
			"&cmbAccounting_Unit_Id="+cmbAcc_UnitCode+"&cmbOffice_Code="+cmbOffice_code+"" +
			"&cmbFinancial_Year="+cmbFinancialYear+""+
			"&cmbStatementName="+cmbStatementName+"&statementGp="+statementGp;
	//alert(url);
	var xmlrequest = getTransport();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);	
}
function manipulate1(xmlrequest) {
	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			 if (command == "getStatementName") { //having 
				getStatementName1(baseResponse);
			} 
			else if (command == "groupch") //having
			{
				groupch_load(baseResponse);
			}
			
			else if (command == "callHead")//having
			{
				Range_Of(baseResponse);
			}

			else if (command=="callstatement")//having
			{
				callstatement_res(baseResponse);
			}
			else if (command=="checkFreeze")//having
			{
				checkFreeze1(baseResponse);
			}else if (command=="getAmount")//having
			{
				getAmount1(baseResponse);
			}else if (command=="head_test")
			{
				head_test_res(baseResponse);
			}
			
		}
	}
}

function callstatement_res(baseResponse)
{
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="already")
				{
					var Allocation_Type=baseResponse.getElementsByTagName("Allocation_Type")[0].firstChild.nodeValue;
					//var regAmt=baseResponse.getElementsByTagName("regAmt")[0].firstChild.nodeValue;
					if(Allocation_Type=="G")
					{
						
						//uncheck radio button
						document.frmBudget.groupId[1].checked=true;
						document.frmBudget.groupId[0].checked=false;
						
						//disable
						document.frmBudget.groupId[0].disabled=true;
						document.frmBudget.groupId[1].disabled=false;
						
						document.getElementById("head_div1").style.display="none";
						document.getElementById("head_div2").style.display="none";
						
					}
					else if(Allocation_Type=="H")
					{
						document.frmBudget.groupId[0].checked=true;
						document.frmBudget.groupId[1].checked=false;
						callHead();
						//disable
						document.frmBudget.groupId[0].disabled=false;
						document.frmBudget.groupId[1].disabled=true;
						
						document.getElementById("head_div1").style.display="block";
						document.getElementById("head_div2").style.display="block";
					}
						
					
			
		
	}else{
		//document.getElementById("reallocation").value="";
		document.frmBudget.groupId[0].checked=false;
		document.frmBudget.groupId[1].checked=false;
		
		document.frmBudget.groupId[0].disabled=false;
		document.frmBudget.groupId[1].disabled=false;
		
		
		document.getElementById("head_div1").style.display="block";
		document.getElementById("head_div2").style.display="block";
	}
}

function head_test_res(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="success")
	{
		 var tt=baseResponse.getElementsByTagName("grouptype")[0].firstChild.nodeValue;
		document.getElementById("groupType").value=tt;
		 var tdesc=baseResponse.getElementsByTagName("groupdesc")[0].firstChild.nodeValue;
			document.getElementById("groupDesc").value=tdesc;
	}else{
		alert("Error in choosing Group Type");
	}
}
function Range_Of(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	var head_code = document.getElementById("txtaccountheadcode");
	head_code.length=0;
    if(flag=="success"){ 
            var Range_Of_Heads = baseResponse.getElementsByTagName("Range_Of_Heads");
            var lengthRange=Range_Of_Heads.length;
            //alert("lengthRange  ="+lengthRange);
	            if(parseInt(lengthRange)==1){
	            	//alert("inside length equals one");
	            	  document.frmBudget.groupId[0].checked=true;
	            	  document.frmBudget.groupId[1].checked=false; 
	            }
            for(var i=0; i<Range_Of_Heads.length; i++)
                {
                    var opt = document.createElement('option');
                    opt.value = Range_Of_Heads[i].firstChild.nodeValue;
                    opt.innerHTML = Range_Of_Heads[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                    head_code.appendChild(opt);
                }
        }
        else
        {
        alert("Head Code Does Not Exists");

    	document.frmBudget.groupDesc.value="";
    	document.frmBudget.groupType.value="";
        var headc=document.getElementById("txtaccountheadcode");
        headc.length=0;
        var op = document.createElement("OPTION");
    	op.value = "";
    	var txt = document.createTextNode("---Choose A/c---");
    	op.appendChild(txt);
    	headc.appendChild(op);   
        document.frmBudget.groupId[0].checked=false;
        document.frmBudget.groupId[1].checked=false;  
   
        
        }
}
function getAmount1(baseResponse)
{
	document.getElementById("txtbudget_alloted").value="";
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success"){
    	var amt = baseResponse.getElementsByTagName("amount")[0].firstChild.nodeValue;
    	document.getElementById("txtbudget_alloted").value=amt;
        }
    else if (flag=="NOAmt"){
    	alert("Amount Doesn't exist");
    }
        else
        {
        alert("Fail To Check");
        
        
        }
}
function checkFreeze1(baseResponse)
{
	document.getElementById("cmdAdd").disabled=false;	
	document.getElementById("cmdUpdate").disabled=false;	
	document.getElementById("cmdDelete").disabled=false;	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success"){
    	var status = baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue;
    	if(status=="Freeze"){
    		alert("Freezed ");
    	}else if(status=="NotFreeze"){
    		
    		document.getElementById("cmdAdd").disabled=true;	
    		document.getElementById("cmdUpdate").disabled=true;	
    		document.getElementById("cmdDelete").disabled=true;	
    		//document.getElementById("cmdUpdate").disabled=true;	
    		alert("Not Freezed ");
    	}
          
        }
        else
        {
        alert("Fail To Check");
        
        
        }
}
function getStatementName1(baseResponse) {

	document.frmBudget.cmbStatementName.length = 1;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len4 = baseResponse.getElementsByTagName("STATEMENT_NO").length;
		for ( var i = 0; i < len4; i++) {
			var STATEMENT_NO = baseResponse
					.getElementsByTagName("STATEMENT_NO")[i].firstChild.nodeValue;
			var STATEMENT_DESC = baseResponse
					.getElementsByTagName("STATEMENT_DESC")[i].firstChild.nodeValue;

			var se = document.getElementById("cmbStatementName");
			var op = document.createElement("OPTION");
			op.value = STATEMENT_NO;
			var txt = document.createTextNode(STATEMENT_DESC);
			op.appendChild(txt);
			se.appendChild(op);
		}
	} else {
		alert("Failed to Load Statement Name");
	}
}
function groupch_load(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	
	
    if(flag=="success"){
    
           var statementgp = document.getElementById("statementGp");
           statementgp.length=0;
            var gp_no = baseResponse.getElementsByTagName("gp_no");
            var gp_desc = baseResponse.getElementsByTagName("gp_desc");
            var opt = document.createElement('option');
            opt.value = "";
            opt.innerHTML ="Select"; //instead of using textnode ,we use innerhtml
            statementgp.appendChild(opt);
            for(var i=0; i<gp_no.length; i++)
                {
                
                    var opt = document.createElement('option');
                    opt.value = gp_no[i].firstChild.nodeValue;
                    opt.innerHTML = gp_desc[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                    statementgp.appendChild(opt);
                }
        }
        else
        {
        alert("No Record Exist");
         var statementgp = document.forms[0].statementGp;
         statementgp.length=0;
       //  document.forms[0].advnumber.value="0";
        
        }
}


//CallServer Function

function doFunction(Command,param)
{   
    var url="";
    if(Command=="checkCode")
    {
        var headcode=document.frmBudget.txtaccountheadcode.value;
        
        url="../../../../../BudgetMasterServlet.con?Command=HeadCode&txtAcc_Head_code="+headcode;
        var req=getTransport();
        req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               headcodeResponse(req);
            }  ; 
                    req.send(null);
    }
    if(Command=="Add")
    {
        var cmbAcc_UnitCode=document.frmBudget.cmbAcc_UnitCode.value;
        var cmbOffice_Code=document.frmBudget.cmbOffice_code.value;
        var cmbFinancial_Year=document.frmBudget.cmbFinancialYear.value;
        var txtaccountheadcode=document.frmBudget.txtaccountheadcode.value;
        var txtpreviousyear=document.frmBudget.txtpreviousyear.value;
        var txtcurrentyearbudget=document.frmBudget.txtcurrentyearbudget.value;
        var txtcurrentyearrevised=document.frmBudget.txtcurrentyearrevised.value;
        var txtbudget_alloted=document.frmBudget.txtbudget_alloted.value;
        var txtbudget_spent=document.frmBudget.txtbudget_spent.value;
        var txtnextyearestimate=document.frmBudget.txtnextyearestimate.value;
        var typallocation;
        if(document.frmBudget.groupId[0].checked==true){
    		typallocation=document.frmBudget.groupId[0].value;
    	}else {
    		typallocation=document.frmBudget.groupId[1].value;
    	}
        var txtreferno=document.frmBudget.txtreferno.value;
        var txtreferdate=document.frmBudget.txtreferdate.value;
        var txtRemarks=document.frmBudget.txtRemarks.value;
        var flag=nullcheck();
        if(flag==true)
        {
        url="../../../../../BudgetMasterServlet.con?Command=Add&cmbAccounting_Unit_Id="+cmbAcc_UnitCode+"&cmbOffice_Code="+cmbOffice_Code+"&cmbFinancial_Year="+cmbFinancial_Year+"&txtAcc_Head_code="+txtaccountheadcode+"&txtPreivous_Year="+txtpreviousyear+"&txtCurrent_Year_Budget="+txtcurrentyearbudget+"&txtCurrent_Year_Revised="+
        txtcurrentyearrevised+"&txtNext_Year_Estimate="+txtnextyearestimate+"&txtRefer_No="+txtreferno+"&txtRefer_Date="+txtreferdate+"&txtRemarks="+txtRemarks+"&txtbudget_alloted="+txtbudget_alloted+"&txtbudget_spent="+txtbudget_spent+"&typallocation="+typallocation;
       //alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           AddRecordResponse(req);
        } ;  
        
        req.send(null);
        }  
        
    }
    
    if(Command=="Update")
    {
    	var cmbAcc_UnitCode=document.frmBudget.cmbAcc_UnitCode.value;
        var cmbOffice_Code=document.frmBudget.cmbOffice_code.value;
        var cmbFinancial_Year=document.frmBudget.cmbFinancialYear.value;
        var txtaccountheadcode=document.frmBudget.txtaccountheadcode.value;
        var txtpreviousyear=document.frmBudget.txtpreviousyear.value;
        var txtcurrentyearbudget=document.frmBudget.txtcurrentyearbudget.value;
        var txtcurrentyearrevised=document.frmBudget.txtcurrentyearrevised.value;
        var txtnextyearestimate=document.frmBudget.txtnextyearestimate.value;
        var txtreferno=document.frmBudget.txtreferno.value;
        var txtreferdate=document.frmBudget.txtreferdate.value;
        var txtRemarks=document.frmBudget.txtRemarks.value; 
        var txtbudget_alloted=document.frmBudget.txtbudget_alloted.value;
        var txtbudget_spent=document.frmBudget.txtbudget_spent.value;
        var flag=nullcheck();
        if(flag==true)
        {
        url="../../../../../BudgetMasterServlet.con?Command=Update&cmbAccounting_Unit_Id="+cmbAcc_UnitCode+"&cmbOffice_Code="+cmbOffice_Code+"&cmbFinancial_Year="+cmbFinancial_Year+"&txtAcc_Head_code="+txtaccountheadcode+"&txtPreivous_Year="+txtpreviousyear+"&txtCurrent_Year_Budget="+txtcurrentyearbudget+"&txtCurrent_Year_Revised="+txtcurrentyearrevised+"&txtNext_Year_Estimate="+
        txtnextyearestimate+"&txtRefer_No="+txtreferno+"&txtRefer_Date="+txtreferdate+"&txtRemarks="+txtRemarks+"&txtbudget_alloted="+txtbudget_alloted+"&txtbudget_spent="+txtbudget_spent;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           UpdateRecordResponse(req);
        }   
        req.send(null);
        }
    }
    
    if(Command=="Delete")
    {
        var cmbFinancial_Year=document.frmBudget.cmbFinancialYear.value;
        var txtaccountheadcode=document.frmBudget.txtaccountheadcode.value;
        if((cmbFinancial_Year=="")||(cmbFinancial_Year=="0")){
        	alert("Enter Financial_Year");
        	return false;
        }else{
        url="../../../../../BudgetMasterServlet.con?Command=Delete&cmbFinancial_Year="+cmbFinancial_Year+"&txtAcc_Head_code="+txtaccountheadcode;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           DeleteRecordResponse(req);
        }   ;
        req.send(null);
    }
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
                var headname=baseResponse.getElementsByTagName("headcode")[0].firstChild.nodeValue;
                document.frmBudget.txtaccountheadname.value="";
                document.frmBudget.txtaccountheadname.value=headname;
            }
            else
            {
                document.frmBudget.txtaccountheadcode.value="";
                document.frmBudget.txtaccountheadname.value="";
                alert("Invalid HeadCode");
                document.frmBudget.txtaccountheadcode.focus();
                
            }
        }
        
    }

}


function AddRecordResponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
            {
                alert("Record Inserted Successfully");
                ClearAll();
                //document.frmBudget.cmbAcc_UnitCode.selectedIndex=0;
                //document.frmBudget.cmbOffice_code.selectedIndex=0;
                document.frmBudget.cmbFinancialYear.selectedIndex=0;
                document.frmBudget.txtaccountheadcode.value="";
                document.frmBudget.txtaccountheadname.value="";
                document.frmBudget.txtpreviousyear.value="";
                document.frmBudget.txtcurrentyearbudget.value="";
                document.frmBudget.txtcurrentyearrevised.value="";
                document.frmBudget.txtnextyearestimate.value="";
                document.frmBudget.txtreferno.value="";
                document.frmBudget.txtreferdate.value="";
                document.frmBudget.txtRemarks.value="";
                document.frmBudget.txtbudget_alloted.value="";
                document.frmBudget.txtbudget_spent.value="";
            }
            else if(flag=="AlreadyExist")
            {
                alert("Record Already Exist for This AccountHead,so Choose some Other AccountHeadCode");
                ClearAll();
                //document.frmBudget.cmbAcc_UnitCode.selectedIndex=0;
                //document.frmBudget.cmbOffice_code.selectedIndex=0;
                document.frmBudget.cmbFinancialYear.selectedIndex=0;
                document.frmBudget.txtaccountheadcode.value="";
                document.frmBudget.txtaccountheadname.value="";
                document.frmBudget.txtpreviousyear.value="";
                document.frmBudget.txtcurrentyearbudget.value="";
                document.frmBudget.txtcurrentyearrevised.value="";
                document.frmBudget.txtnextyearestimate.value="";
                document.frmBudget.txtreferno.value="";
                document.frmBudget.txtreferdate.value="";
                document.frmBudget.txtRemarks.value="";
                document.frmBudget.txtbudget_alloted.value="";
                document.frmBudget.txtbudget_spent.value="";
            }
            else if(flag=="failure")
            {
                alert("Record Can't Inserted");
                
            }
            
        }
   }

}

function UpdateRecordResponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
            {
                alert("Record Updated Successfully");
                ClearAll();
                //document.frmBudget.cmbAcc_UnitCode.selectedIndex=0;
                //document.frmBudget.cmbOffice_code.selectedIndex=0;
                document.frmBudget.cmbFinancialYear.selectedIndex=0;
                document.frmBudget.txtaccountheadcode.value="";
                document.frmBudget.txtaccountheadname.value="";
                document.frmBudget.txtpreviousyear.value="";
                document.frmBudget.txtcurrentyearbudget.value="";
                document.frmBudget.txtcurrentyearrevised.value="";
                document.frmBudget.txtnextyearestimate.value="";
                document.frmBudget.txtreferno.value="";
                document.frmBudget.txtreferdate.value="";
                document.frmBudget.txtRemarks.value="";
                document.frmBudget.txtbudget_alloted.value="";
                document.frmBudget.txtbudget_spent.value="";
                
                var d=document.getElementById("cmdUpdate");
                d.style.display="none";
                
                var d2=document.getElementById("cmdDelete");
                d2.style.display="none";
                
                var d1=document.getElementById("cmdAdd");
                d1.style.display="block";
            }
            else
            {
                alert("Record Not Updated");
            }
        }
    }
}


function DeleteRecordResponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
            {
                alert("Record Deleted Successfully");
                ClearAll();
                //document.frmBudget.cmbAcc_UnitCode.selectedIndex=0;
                //document.frmBudget.cmbOffice_code.selectedIndex=0;
                document.frmBudget.cmbFinancialYear.selectedIndex=0;
                document.frmBudget.txtaccountheadcode.value="";
                document.frmBudget.txtaccountheadname.value="";
                document.frmBudget.txtpreviousyear.value="";
                document.frmBudget.txtcurrentyearbudget.value="";
                document.frmBudget.txtcurrentyearrevised.value="";
                document.frmBudget.txtnextyearestimate.value="";
                document.frmBudget.txtreferno.value="";
                document.frmBudget.txtreferdate.value="";
                document.frmBudget.txtRemarks.value="";
                document.frmBudget.txtbudget_alloted.value="";
                document.frmBudget.txtbudget_spent.value="";
                var d=document.getElementById("cmdUpdate");
                d.style.display="none";
                
                var d2=document.getElementById("cmdDelete");
                d2.style.display="none";
                
                var d1=document.getElementById("cmdAdd");
                d1.style.display="block";
            }
            else
            {
                alert("Record Not Deleted");
            }
        }
    }

}



/************************************************* Function for Getting Values from Opener Window*******************************************************************/

function List(accountingunitid,accountofficecode,financialyear,headcode,previousyear,currentyearbudget,currentyearrevised,nextyear,referno,referdate,remarks,budgetalloted,budgetspent,allotype)
{
	 //alert(allotype);
	 var al=allotype;

	 document.frmBudget.groupId[0].disabled=true;
		document.frmBudget.groupId[1].disabled=true;
	 document.getElementById("head_div1").style.display="none";
	document.getElementById("head_div2").style.display="none";
	document.frmBudget.groupId.value=allotype;
    document.frmBudget.cmbAcc_UnitCode.value=accountingunitid;
    document.frmBudget.cmbOffice_code.value=accountofficecode;
    document.frmBudget.cmbFinancialYear.value=financialyear;
  //  var hhc=headcode;
    var hhcode = document.getElementById("txtaccountheadcode");
    hhcode.length=0;
  /* var gp_no = baseResponse.getElementsByTagName("gp_no");
    var gp_desc = baseResponse.getElementsByTagName("gp_desc");*/
    var opt = document.createElement('option');
    //opt.value = "";
    //opt.innerHTML ="Select"; 
    opt.value=headcode; 
    opt.innerHTML=headcode;  
    hhcode.appendChild(opt);
    reallocation_fn(); 
    //document.getElementById("txtaccountheadcode").va
    //document.frmBudget.txtaccountheadcode.value=headcode;
    //document.frmBudget.txtaccountheadcode.text=headcode;
    //document.getElementById("txtaccountheadcode").options[document.getElementById("txtaccountheadcode").selectedIndex].text=headcode;
  //  document.getElementById("txtaccountheadcode").options[document.getElementById("txtaccountheadcode").selectedIndex].value=headcode;
    
   // document.getElementById("txtaccountheadcode").text=headcode;
   // document.getElementById("txtaccountheadcode").value=headcode;
 //   doFunction('checkCode','null');
    document.frmBudget.txtpreviousyear.value=previousyear;
    document.frmBudget.txtcurrentyearbudget.value=currentyearbudget;
    document.frmBudget.txtcurrentyearrevised.value=currentyearrevised;
    document.frmBudget.txtnextyearestimate.value=nextyear;
    document.frmBudget.txtbudget_alloted.value=budgetalloted;
    document.frmBudget.txtbudget_spent.value=budgetspent;
    
	
    if(referno!="null")
    {
    document.frmBudget.txtreferno.value=referno;
    }
    if(referdate!="Not Specified")
    {
    document.frmBudget.txtreferdate.value=referdate;
    }
    document.frmBudget.txtRemarks.value=remarks;
    
    
    var d=document.getElementById("cmdUpdate");
    d.style.display="block";
    
    var d2=document.getElementById("cmdDelete");
    d2.style.display="block";
    
    var d1=document.getElementById("cmdAdd");
    d1.style.display="none";
    
}

//**************************Validation Checking************************************//
function sixdigit(val)
{

 if(val.length!=0)
    {
        if(val.length<6)
        {
        alert("Account Head Code shouldn't be less than 6 digit number");
        return false;
        }
        return true;
    }
}


function nullcheck()
{
    if((document.frmBudget.cmbAcc_UnitCode.value=="")||(document.frmBudget.cmbAcc_UnitCode.value.length<=0)||(document.frmBudget.cmbAcc_UnitCode.value=="0"))
    {
        alert("Select Accounting Unit Id");
        document.frmBudget.cmbAcc_UnitCode.focus();
        return false;
    }
    
    if((document.frmBudget.cmbOffice_code.value=="") || (document.frmBudget.cmbOffice_code.value.length<=0) || (document.frmBudget.cmbOffice_code.value=="0"))
    {
        alert("Select Office Code");
        document.frmBudget.cmbOffice_code.focus();
        return false;
    
    }
    if((document.frmBudget.cmbFinancialYear.value=="") || (document.frmBudget.cmbFinancialYear.value.length<=0) || (document.frmBudget.cmbFinancialYear.value=="0"))
    {
        alert("Select Financial Year");
        documnet.frmBudget.cmbFinancialYear.focus();
        return false;
    }
    
    if((document.frmBudget.txtaccountheadcode.value=="") || (document.frmBudget.txtaccountheadcode.value.length<=0))
    {
        alert("Please Select or Enter Account Head Code");
        document.frmBudget.txtaccountheadcode.focus();
        return false;
    }
    return true;
}
//Financial Year Coding Part ///

function loadfyr()
{
         var cyr, cdt,cdt1;
 	cdt=new Date();
 	cyr=cdt.getFullYear();
 	cmn=cdt.getMonth();
        //alert("cdate"+cdt);
        //alert("cmonth"+cmn);
        //alert("cyear"+cyr);
        var cmbFinancialYear=document.getElementById("cmbFinancialYear");
        cyr=cyr+1
 	if (parseInt(cmn) <= 2)
        {
  
                document.frmBudget.cmbFinancialYear.length=5;
                cmbFinancialYear.innerHTML="";
               /* var option=document.createElement("OPTION");
                option.text="--Select FinancialYear--";
                option.value=0;*/
                for (var i = 0 ; i < 1; i++) 
                {
         
                  //document.frmBudget.cmbFinancialYear.options[i].text=(cyr-2)+"-"+(cyr-1);
                  //document.frmBudget.cmbFinancialYear.options[i].value=(cyr-2)+"-"+(cyr-1);
                  var id=(cyr-2)+"-"+(cyr-1);
                  var option=document.createElement("OPTION");
                  option.text=id;
                  option.value=id;
                  try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        } 
                  
                  cyr--;
                }
 	}
 	else 
 	{
           document.frmBudget.cmbFinancialYear.length=5;
           cmbFinancialYear.innerHTML="";
         /*  var option=document.createElement("OPTION");
           option.text="--Select FinancialYear--";
           option.value=0;*/
            for (var i = 0 ; i < 1; i++) 
            {
              //document.frmBudget.cmbFinancialYear.options[i].text=(cyr-1)+"-"+(cyr);
              //document.frmBudget.cmbFinancialYear.options[i].value=(cyr-1)+"-"+(cyr);
              var id=(cyr-1)+"-"+(cyr);
              var option=document.createElement("OPTION");
              option.text=id;
              option.value=id;
                  try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        }
              cyr--;
            }
 	}
        
}

//********************************** Numbers Only Checking *****************************//

function numbersonly1(e,t)
    {
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false ;
        }
     }

function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false ;
        }
     }
     
     
//*********************************************Date Validation Coding Checking*************************************************************//


function getCurrentYear() {
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
  }

  function getCurrentMonth() {
    return new Date().getMonth() + 1;
  } 

  function getCurrentDay() {
    return new Date().getDate();
  }

function isValidDate(dateStr) {
	  
	  // Checks for the following valid date formats:
	  // MM/DD/YYYY
	  // Also separates date into month, day, and year variables
	  var datePat = /^(\d{2,2})(\/)(\d{2,2})\2(\d{4}|\d{4})$/;
	  
	  var matchArray = dateStr.match(datePat); // is the format ok?
	  if (matchArray == null) {
	   alert("Date must be in MM/DD/YYYY format")
	   return false;
	  }
	  
	  month = matchArray[3]; // parse date into variables
	  day = matchArray[1];
	  year = matchArray[4];
	  if (month < 1 || month > 12) { // check month range
	   alert("Month must be between 1 and 12");
	   return false;
	  }
	  if (day < 1 || day > 31) {
	   alert("Day must be between 1 and 31");
	   return false;
	  }
	  if ((month==4 || month==6 || month==9 || month==11) && day==31) {
	   alert("Month "+month+" doesn't have 31 days!")
	   return false;
	  }
	  if (month == 2) { // check for february 29th
	   var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
	   if (day>29 || (day==29 && !isleap)) {
	    alert("February " + year + " doesn't have " + day + " days!");
	    return false;
	     }
	  }
	  return true;  // date is valid
	 }
  
  
function checkdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
//        try{
//        var f=DateFormat(t,c,event,true,'3');
//        }catch(e){
         
       ///New code implemented on 28-03-2019  for year 2019 wrongly displayed 201 
         try{
             var f=isValidDate(c);
            }
        catch(e){
         
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to '+ _Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
        
        
        //exception end
        
        }
        if( f==true)
        {
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
         
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                    t.value="";
                    return false;
                }
        }
            
    }
    else
    {
            alert('Date format  should be (dd/mm/yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    
} 

function ClearAll()
    {
        //document.firmsForm.cmbAcc_UnitCode.value="";
        //document.firmsForm.comOffId.selectedIndex=0;
        document.frmBudget.cmbAcc_UnitCode.selectedIndex=0;
        document.frmBudget.cmbOffice_code.selectedIndex=0;
        document.frmBudget.cmbFinancialYear.selectedIndex=0;
        document.frmBudget.txtaccountheadcode.value="";
        document.frmBudget.txtaccountheadname.value="";
        document.frmBudget.txtpreviousyear.value="";
        document.frmBudget.txtcurrentyearbudget.value="";
        document.frmBudget.txtcurrentyearrevised.value="";
        document.frmBudget.txtnextyearestimate.value="";
        document.frmBudget.txtreferno.value="";
        document.frmBudget.txtreferdate.value="";
        document.frmBudget.txtRemarks.value="";
        document.frmBudget.txtbudget_alloted.value="";
        document.frmBudget.txtbudget_spent.value="";
        var d=document.getElementById("cmdAdd");
        d.style.display="block";
    
        var d1=document.getElementById("cmdUpdate");
        d1.style.display="none";
        
        var d3=document.getElementById("cmdDelete");
        d3.style.display="none";
        
    }
    


/////////////////////////////////////////////////////  Amount limitation 
function limit_amt(field,e)
{
      var unicode=e.charCode? e.charCode : e.keyCode;
      if(field.value.length<17)
      {
        if(field.value.length==14 && field.value.indexOf('.')==-1  )
        field.value=field.value+'.';
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<46 || unicode==47 || unicode>57   ) 
                return false 
        }
      }
      else 
      return false;
      
}




///////////////////////////////////////////  valid amount or not
function valid_amt(field)
{
    
    amt=field.value;
    if(amt.indexOf(".")!=amt.lastIndexOf("."))
    {
        alert("Enter a Valid Amount");
        field.value="";
        field.focus();
        
    }
}